package kr.co.sootechsys.scrobot.misc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kr.co.sootechsys.scrobot.service.JwtService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtCheckInterceptor implements HandlerInterceptor {
  @Value("${app.jwt.enabled}")
  private boolean enabled;

  private JwtService jwtService;

  public JwtCheckInterceptor(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // options는 검사에서 제외
    if ("OPTIONS".equals(request.getMethod())) {
      return true;
    }

    if (!enabled) {
      return true;
    }

    // return true;

    // log.debug("URI {} {}", request.getRequestURI(), request.getMethod());

    // Iterator<String> iter = request.getHeaderNames().asIterator();
    // while (iter.hasNext()) {
    // String key = iter.next();
    // log.debug("{} {}", key, request.getHeader(key));

    // }

    String authorization = request.getHeader(JwtService.HEADER_NAME);

    if (null == authorization || !authorization.startsWith(JwtService.TOKEN_PREFIX)) {
      log.debug("NULL AUTHORIZATION - {} {}", request.getMethod(), request.getRequestURI());
      return false;
    }

    String token = authorization.replaceAll(JwtService.TOKEN_PREFIX, "");
    boolean b = jwtService.varifyToken(token);

    return b;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable ModelAndView modelAndView) throws Exception {
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable Exception ex) throws Exception {
  }

}
