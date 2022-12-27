package com.spring.advanced.proxy.pureproxy.concreteproxy;

import com.spring.advanced.proxy.pureproxy.concreteproxy.code.ConcreteClient;
import com.spring.advanced.proxy.pureproxy.concreteproxy.code.ConcreteLogic;
import com.spring.advanced.proxy.pureproxy.concreteproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

    @Test
    void noProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(concreteLogic);
        client.execute();
    }

    @Test
    void addProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic(); //구체 클래스
        TimeProxy timeProxy = new TimeProxy(concreteLogic); // ConcreteLogic을 상속받은 놈
        ConcreteClient client = new ConcreteClient(timeProxy);
        client.execute();
    }
}
