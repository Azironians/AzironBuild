package lib.duplexMap;

import java.util.HashMap;
import java.util.Map;

public final class DuplexMap<O, P> {

    private final Map<O, P> objectVsProxyMap;

    private final Map<P, O> proxyVsObjectMap;

    public DuplexMap() {
        this.objectVsProxyMap = new HashMap<>();
        this.proxyVsObjectMap = new HashMap<>();
    }

    public final void bind(final O object, final P proxy) {
        this.objectVsProxyMap.put(object, proxy);
        this.proxyVsObjectMap.put(proxy, object);
    }
    
    public final void remove(final Object o){
        final P proxy = objectVsProxyMap.get(o);
        final O object = proxyVsObjectMap.get(o);
        this.removeByObject(object);
        this.removeByProxy(proxy);
    }

    public final P getProxy(final Object o){
        return objectVsProxyMap.get(o);
    }
    
    private void removeByObject(final O object) {
        final P proxy = objectVsProxyMap.get(object);
        this.objectVsProxyMap.remove(object, proxy);
        this.proxyVsObjectMap.remove(proxy, object);
    }

    private void removeByProxy(final P proxy) {
        final O object = proxyVsObjectMap.get(proxy);
        this.proxyVsObjectMap.remove(proxy, object);
        this.objectVsProxyMap.remove(object, proxy);
    }

    public final boolean contains(final Object o) {
        final P proxy = objectVsProxyMap.get(o);
        final O object = proxyVsObjectMap.get(o);
        return containsByObject(object) || containsByProxy(proxy);
    }

    private boolean containsByObject(final O object) {
        final P proxy = objectVsProxyMap.get(object);
        return proxyVsObjectMap.get(proxy) == object;
    }

    private boolean containsByProxy(final P proxy) {
        final O object = proxyVsObjectMap.get(proxy);
        return objectVsProxyMap.get(object) == proxy;
    }
}