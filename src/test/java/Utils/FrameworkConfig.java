package Utils;

import org.aeonbits.owner.Config;

@Config.Sources(value = "file:${user.dir}/src/main/resources/config.properties")
public interface FrameworkConfig extends Config {

    String browserName();
    String url();
    String osName();
    String headless();

    String email();
    String password();

}
