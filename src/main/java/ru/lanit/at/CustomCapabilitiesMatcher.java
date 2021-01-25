package ru.lanit.at;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.grid.internal.utils.CapabilityMatcher;
import org.openqa.selenium.remote.CapabilityType;
import ru.lanit.at.validation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;

public class CustomCapabilitiesMatcher implements CapabilityMatcher {

    private final List<Validator> validators = new ArrayList<>();
    {
        validators.addAll(Arrays.asList(
            new AliasedPropertyValidator(BROWSER_NAME, "browser"),
            new VersionValidator(CapabilityType.BROWSER_VERSION),
            new PlatformValidator(),
            new VersionValidator(CapabilityType.VERSION),
            new VersionValidator(MobileCapabilityType.PLATFORM_VERSION),
            new SimplePropertyValidator(CapabilityType.APPLICATION_NAME),
            new AppiumPropertyValidator(MobileCapabilityType.UDID),
            new AppiumPropertyValidator(MobileCapabilityType.DEVICE_NAME)
        ));
    }

    @Override
    public boolean matches(Map<String, Object> providedCapabilities, Map<String, Object> requestedCapabilities) {

        return providedCapabilities != null && requestedCapabilities != null
                && validators.stream().allMatch(v -> v.apply(providedCapabilities, requestedCapabilities));
    }
}
