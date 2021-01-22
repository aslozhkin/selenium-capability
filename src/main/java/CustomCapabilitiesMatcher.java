import org.openqa.grid.internal.utils.DefaultCapabilityMatcher;

import java.util.*;

public class CustomCapabilitiesMatcher extends DefaultCapabilityMatcher {
    @Override
    public boolean matches(Map<String, Object> nodeCapability, Map<String, Object> requestedCapability) {
        final String deviceName = "deviceName";
        final String deviceOSVersion = "deviceOSVersion";

        // If the request does not have the special capability,
        // we return what the DefaultCapabilityMatcher returns
        if (requestedCapability.containsKey(deviceName)) {
            return matcher(nodeCapability, requestedCapability, deviceName, deviceName);
        } else if (requestedCapability.containsKey(deviceOSVersion)) {
            return matcher(nodeCapability, requestedCapability, deviceOSVersion, deviceOSVersion);
        } else {
            return super.matches(nodeCapability, requestedCapability);
        }
    }

    private boolean matcher(Map<String, Object> nodeCapability, Map<String, Object> requestedCapability, String nCap, String rCap) {
        if (!nodeCapability.containsKey(nCap)) return false;
        String expected = (String) requestedCapability.get(rCap);
        String actual = (String) nodeCapability.get(nCap);
        return Objects.equals(expected, actual);
    }
}
