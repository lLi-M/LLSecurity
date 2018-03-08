package ll.security.chonggou;

import ll.security.vaildcode.VaildCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.Map;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/14
  Time: 12:02
*/
@SuppressWarnings("uncheck")
@Component
public abstract class AbstractVaildProcessor<T extends Vailcode> implements AllCreateVaildInterface {

    //Spring的依赖采集，自动收集实现了AllCodeGenertorInterface接口的类，key为名字，value为该类

    @Autowired
    private Map<String,AllCodeGeneratorInterface> allCodeGenerators;

    SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    public void create(ServletWebRequest request) throws IOException {
        T vailcode = generator();
        saveCode(request, vailcode);
        send(request, vailcode);
    }

    public abstract void send(ServletWebRequest webRequest, T vailcode) throws IOException;

    public abstract void saveCode(ServletWebRequest request, T vailcode);
    public T generator() {
        String type=getVaildType();
        //从Map中获得Spring的依赖查找中注入的类
        Vailcode vailcode = allCodeGenerators.get(type + "CodeGenerator").createCode();

        return (T) vailcode;
    }

    @Override

    public void vaild(ServletWebRequest request, String usercode) {
        String type =getVaildType();
        T vaildcode = (T) sessionStrategy.getAttribute(request,SaveEnum.valueOf(type).toString());
        System.out.println("进入验证方法");
        if (vaildcode==null){
            throw new VaildCodeException("请检查是否获取验证码？");
        }

        if (StringUtils.isEmpty(vaildcode.getCode())) {
            throw new VaildCodeException("验证码不能为空");
        }

        if (vaildcode.expireTimeIspast()){
            throw new VaildCodeException("验证码已经过期，请重新获取");
        }

        if (!StringUtils.equals(usercode, vaildcode.getCode())) {
            throw new VaildCodeException("您输入的验证码不匹配");
        }


        }

    public String getVaildType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "Processor").toLowerCase();
        return type;
    }
}
