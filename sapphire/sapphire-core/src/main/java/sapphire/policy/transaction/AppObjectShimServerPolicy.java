package sapphire.policy.transaction;

import sapphire.common.AppObject;
import sapphire.common.Utils;
import sapphire.policy.SapphirePolicy;
import sapphire.policy.SapphirePolicyUpcalls.SapphireServerPolicyUpcalls;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * server policy that directly works on the deep-cloned copy of an AppObject
 */
public class AppObjectShimServerPolicy implements SapphireServerPolicyUpcalls{
    private AppObject appObject;

    @Override
    public void onCreate(SapphirePolicy.SapphireGroupPolicy group) {

    }

    @Override
    public SapphirePolicy.SapphireGroupPolicy getGroup() {
        return null;
    }

    @Override
    public Object onRPC(String method, ArrayList<Object> params) throws Exception {
        return appObject.invoke(method, params);
    }

    @Override
    public void onMembershipChange() {

    }

    /**
     * gets the app object referenced by the server policy
     * @return the app object
     */
    public AppObject getAppObject() {
        return this.appObject;
    }

    private AppObjectShimServerPolicy(AppObject appObject) {
        this.appObject = appObject;
    }

    /**
     *  creates server policy which contains deep copy of the input app object
     * @param appObject the origin app object
     */
    public static AppObjectShimServerPolicy cloneInShimServerPolicy(AppObject appObject) throws Exception {
        AppObject deepCloneAppObject = (AppObject)Utils.ObjectCloner.deepCopy(appObject);
        return new AppObjectShimServerPolicy(deepCloneAppObject);
    }
}