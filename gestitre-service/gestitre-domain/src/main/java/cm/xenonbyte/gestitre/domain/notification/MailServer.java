package cm.xenonbyte.gestitre.domain.notification;

import cm.xenonbyte.gestitre.domain.common.entity.BaseEntity;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Active;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.MailServerId;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.Password;
import cm.xenonbyte.gestitre.domain.notification.vo.Host;
import cm.xenonbyte.gestitre.domain.notification.vo.MailServerState;
import cm.xenonbyte.gestitre.domain.notification.vo.MailServerType;
import cm.xenonbyte.gestitre.domain.notification.vo.Port;
import cm.xenonbyte.gestitre.domain.notification.vo.Protocol;
import cm.xenonbyte.gestitre.domain.notification.vo.UseAuth;
import cm.xenonbyte.gestitre.domain.notification.vo.UseSSL;
import cm.xenonbyte.gestitre.domain.notification.vo.Username;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import static cm.xenonbyte.gestitre.domain.notification.vo.MailServerState.CONFIRM;
import static cm.xenonbyte.gestitre.domain.notification.vo.MailServerState.NEW;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
public final class MailServer extends BaseEntity<MailServerId> {
    private final Name name;
    private final Email from;
    private final MailServerType type;
    private final Host host;
    private final Port port;
    private final Protocol protocol;
    private ZonedDateTime creationAt;
    private UseSSL useSSL;
    private UseAuth useAuth;
    private Username username;
    private Password password;
    private MailServerState state;
    private ZonedDateTime confirmedAt;
    private Active active;

    public MailServer(Name name, Email from, MailServerType type, Host host, Port port, Protocol protocol) {
        this.name = Objects.requireNonNull(name);
        this.from = Objects.requireNonNull(from);
        this.type = Objects.requireNonNull(type);
        this.host = Objects.requireNonNull(host);
        this.port = Objects.requireNonNull(port);
        this.protocol = Objects.requireNonNull(protocol);
    }

    private MailServer(Builder builder) {
        setId(builder.id);
        name = builder.name;
        from = builder.from;
        type = builder.type;
        host = builder.host;
        port = builder.port;
        protocol = builder.protocol;
        creationAt = builder.creationAt;
        useSSL = builder.useSSL;
        useAuth = builder.useAuth;
        username = builder.username;
        password = builder.password;
        state = builder.state;
        confirmedAt = builder.confirmedAt;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Name getName() {
        return name;
    }

    public Email getFrom() {
        return from;
    }

    public MailServerType getType() {
        return type;
    }

    public Host getHost() {
        return host;
    }

    public Port getPort() {
        return port;
    }

    public ZonedDateTime getCreationAt() {
        return creationAt;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public UseSSL getUseSSL() {
        return useSSL;
    }

    public UseAuth getUseAuth() {
        return useAuth;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public MailServerState getState() {
        return state;
    }

    public ZonedDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public Active getActive() {
        return active;
    }

    public void validateMandatoryFields() {
        Assert.field("Name", name)
                .notNull();

        Assert.field("From", from)
                .notNull();

        Assert.field("Type", type)
                .notNull();

        Assert.field("Host", host)
                .notNull();

        Assert.field("Port", port)
                .notNull();

        Assert.field("Protocol", protocol)
                .notNull();

        if( useAuth != null  && useAuth.value()) {
            Assert.field("Username", username)
                    .notNull();
            Assert.field("Password", password)
                    .notNull();
        }
    }

    public void initializeDefaultValues() {
        setId(new MailServerId(UUID.randomUUID()));
        active = Active.with(true);
        creationAt = ZonedDateTime.now();
        state = NEW;
        if(useSSL == null) {
            useSSL = UseSSL.with(false);
        }
        if(useAuth == null) {
            useAuth = UseAuth.with(false);
        }
    }

    public void confirm() {
        state = CONFIRM;
        confirmedAt = ZonedDateTime.now();
    }


    public static final class Builder {
        private MailServerId id;
        private Name name;
        private Email from;
        private MailServerType type;
        private Host host;
        private Port port;
        private Protocol protocol;
        private ZonedDateTime creationAt;
        private UseSSL useSSL;
        private UseAuth useAuth;
        private Username username;
        private Password password;
        private MailServerState state;
        private ZonedDateTime confirmedAt;
        private Active active;

        private Builder() {
        }

        public Builder id(MailServerId val) {
            id = val;
            return this;
        }

        public Builder name(Name val) {
            name = val;
            return this;
        }

        public Builder from(Email val) {
            from = val;
            return this;
        }

        public Builder type(MailServerType val) {
            type = val;
            return this;
        }

        public Builder host(Host val) {
            host = val;
            return this;
        }

        public Builder port(Port val) {
            port = val;
            return this;
        }

        public Builder protocol(Protocol val) {
            protocol = val;
            return this;
        }

        public Builder creationAt(ZonedDateTime val) {
            creationAt = val;
            return this;
        }

        public Builder useSSL(UseSSL val) {
            useSSL = val;
            return this;
        }

        public Builder useAuth(UseAuth val) {
            useAuth = val;
            return this;
        }

        public Builder username(Username val) {
            username = val;
            return this;
        }

        public Builder password(Password val) {
            password = val;
            return this;
        }

        public Builder state(MailServerState val) {
            state = val;
            return this;
        }

        public Builder confirmedAt(ZonedDateTime val) {
            confirmedAt = val;
            return this;
        }

        public Builder active(Active val) {
            active = val;
            return this;
        }

        public MailServer build() {
            return new MailServer(this);
        }
    }
}
