package executor.service.model.configs;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ThreadPoolConfig {
    private Integer corePoolSize;
    private Long keepAliveTime;

    private TimeUnit timeUnit;

    public ThreadPoolConfig() {}

    public ThreadPoolConfig(Integer corePoolSize,Long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = TimeUnit.SECONDS;
    }

    public ThreadPoolConfig(Integer corePoolSize,Long keepAliveTime, TimeUnit timeUnit) {
        this.corePoolSize = corePoolSize;
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThreadPoolConfig that = (ThreadPoolConfig) o;
        return Objects.equals(corePoolSize, that.corePoolSize) && Objects.equals(keepAliveTime, that.keepAliveTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(corePoolSize, keepAliveTime);
    }
}
