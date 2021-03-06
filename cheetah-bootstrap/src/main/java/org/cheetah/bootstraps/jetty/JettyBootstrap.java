package org.cheetah.bootstraps.jetty;

import org.cheetah.bootstraps.BootstrapException;
import org.cheetah.bootstraps.BootstrapSupport;
import org.cheetah.commons.logger.Info;
import org.cheetah.commons.logger.Loggers;
import org.cheetah.configuration.Configuration;
import org.cheetah.configuration.ConfigurationFactory;
import org.cheetah.ioc.spring.web.CheetahContextLoaderListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Servlet;

/**
 * @author Max
 */
public class JettyBootstrap extends BootstrapSupport {

    public static final String PORT_KEY = "http.port";
    public static final String IDLE_TIMEOUT_KEY = "http.idle.timeout";
    public static final String CONTEXT_PATH_KEY = "http.context.path";
    public static final String ACCEPT_QUEUE_SIZE_KEY = "http.accept.queue.size";
    public static final String MIN_THREADS = "server.min.threads";
    public static final String MAX_THREADS = "server.man.threads";
    public static final String SERVER_DESCRIPTOR = "server.web.xml";
    public static final String SERVER_WEBAPP_PATH  = "server.webapp.path";

    public static final int DEFAULT_PORT = 8000;
    public static final int DEFAULT_ACCEPT_QUEUE_SIZE = 512;
    public static final long DEFAULT_IDLE_TIMEOUT = 30000;
    public static final String DEFAULT_CONTEXT_PATH = "/";
    public static final String DEFAULT_SERVER_DESCRIPTOR = "./webapp/WEB-INF/web.xml";
    public static final String DEFAULT_SERVER_WEBAPP_PATH = "./webapp";

    private Configuration configuration;
    private String applicationConfig = "classpath:META-INF/application.xml";
    private JettyServerConfig serverConfig;
    private Server server;
    private ServerConnector serverConnector;
    WebAppContext webAppContext;
    private Class<? extends Servlet> dispatcher;

    public JettyBootstrap() {
        this(new ConfigurationFactory().fromClasspath("/application.properties"));
    }

    public JettyBootstrap(Class<? extends Servlet> dispatcher) {
        this(new ConfigurationFactory().fromClasspath("/application.properties"));
        this.dispatcher = dispatcher;
    }

    public JettyBootstrap(String applicationConfig, JettyServerConfig serverConfig) {
        this.applicationConfig = applicationConfig;
        this.serverConfig = serverConfig;
    }

    public JettyBootstrap(Configuration configuration) {
        this.configuration = configuration;
        initialize();
    }

    public JettyBootstrap(Configuration configuration, String applicationConfig) {
        this.configuration = configuration;
        this.applicationConfig = applicationConfig;
        initialize();
    }

    @Override
    protected void startup() throws Exception {
        try {
            server = new Server(new QueuedThreadPool(serverConfig.maxThreads(), serverConfig.minThreads()));
            serverConnector = new ServerConnector(server);
            configServerConnector();

            webAppContext = new WebAppContext();
            configWebAppContext();

            server.addConnector(serverConnector);
            server.setHandler(webAppContext);
            server.start();
        } catch (Exception e) {
            throw new BootstrapException("jetty boot occurs error.", e);
        }
    }

    private void initialize() {
        int maxThreads = configuration.getInt(MAX_THREADS, 256);
        int minThreads = configuration.getInt(MIN_THREADS, Runtime.getRuntime().availableProcessors() * 2);
        int port = configuration.getInt(PORT_KEY, DEFAULT_PORT);
        long timeout = configuration.getLong(IDLE_TIMEOUT_KEY, DEFAULT_IDLE_TIMEOUT);
        int acceptQueueSize = configuration.getInt(ACCEPT_QUEUE_SIZE_KEY, DEFAULT_ACCEPT_QUEUE_SIZE);
        String contextPath = configuration.getString(CONTEXT_PATH_KEY, DEFAULT_CONTEXT_PATH);
        String descriptor = configuration.getString(SERVER_DESCRIPTOR, DEFAULT_SERVER_DESCRIPTOR);
        String webappPath = configuration.getString(SERVER_WEBAPP_PATH, DEFAULT_SERVER_WEBAPP_PATH);

        serverConfig = JettyServerConfig.newBuilder()
                .acceptQueueSize(acceptQueueSize)
                .contextPath(contextPath)
                .descriptor(descriptor)
                .maxThreads(maxThreads)
                .minThreads(minThreads)
                .port(port)
                .timeout(timeout)
                .webappPath(webappPath)
                .build();
        Info.log(this.getClass(), "jetty server config: {}", serverConfig.toString());
    }

    @Override
    protected void shutdown() {
        super.shutdown();
        stop();
    }

    private void configServerConnector() {
        serverConnector.setReuseAddress(true);
        serverConnector.setPort(serverConfig.port());
        serverConnector.setIdleTimeout(serverConfig.timeout());
        serverConnector.setAcceptQueueSize(serverConfig.acceptQueueSize());
    }

    private void configWebAppContext() {
        webAppContext.setContextPath(serverConfig.contextPath());    //设置上下文根路径
        setContextLoaderListener();
        webAppContext.addFilter(createEncodingFilter(), "/*", null);  //添加编码过滤器，解决中文问题
        setDispatcher(); //引入Apache CXF、Jersey，提供Restful Web Service能力
        webAppContext.setDescriptor(serverConfig.descriptor());
        webAppContext.setResourceBase(serverConfig.webappPath());
    }

    protected void setContextLoaderListener() {
        webAppContext.addEventListener(new CheetahContextLoaderListener());  //提供Spring支持能力
        webAppContext.setInitParameter("contextConfigLocation", applicationConfig);    //Spring配置文件位置
    }

    protected void setDispatcher() {
        webAppContext.addServlet(dispatcher, "/*");
    }

    private FilterHolder createEncodingFilter() {
        FilterHolder encodingHolder = new FilterHolder();
        encodingHolder.setFilter(new CharacterEncodingFilter());
        encodingHolder.setInitParameter("encoding", "UTF-8");
        encodingHolder.setInitParameter("forceEncoding", "true");
        return encodingHolder;
    }

    private void stop() {
        if (null != server) {
            try {
                server.stop();
            } catch (Exception e) {
                Loggers.me().error(getClass(), "stop jetty server occurs error.", e);
            }
        }
    }



}
