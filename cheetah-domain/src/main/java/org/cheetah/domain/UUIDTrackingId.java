package org.cheetah.domain;

/**
 * UUID类型的ID基类
 * Created by Max on 2015/12/25.
 */
public class UUIDTrackingId extends TrackingId<String> {
//    @Column(name = "id")
    protected String id;

    public UUIDTrackingId() {
    }

    public UUIDTrackingId(String id) {
        this.id = id;
    }

    @Override
    public boolean sameAs(TrackingId other) {
        return other != null && this.id().equals(other.id());
    }

    @Override
    public String id() {
        return id;
    }

}
