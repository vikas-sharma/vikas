package com.vikas.config;

import java.util.Locale;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * 
 * @author Vikas Sharma
 * 
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	@Value("${recaptcha.publicKey}")
	private String publicKey;

	@Value("${recaptcha.privateKey}")
	private String privateKey;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// Register "global" interceptor beans to apply to all registered
		// HandlerMappings

		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		registry.addInterceptor(lci);

		ThemeChangeInterceptor tci = new ThemeChangeInterceptor();
		tci.setParamName("theme");

		registry.addInterceptor(tci);
	}

	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false).favorParameter(true);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		super.addViewControllers(registry);

		registry.addViewController("/").setViewName("home");
		registry.addViewController("/login.htm").setViewName("login");
		registry.addViewController("/success.htm").setViewName("success");
		registry.addViewController("/chess/index.htm").setViewName("chess");
		registry.addViewController("/chess/favourites.htm").setViewName(
				"favourites");
		registry.addViewController("/personal/index.htm").setViewName(
				"personal");
		registry.addViewController("/personal/myPhotos.htm").setViewName(
				"myPhotos");
		registry.addViewController("/personal/familyPhotos.htm").setViewName(
				"familyPhotos");
		registry.addViewController("/technology/index.htm").setViewName(
				"technology");
		registry.addViewController("/chess/playChess.htm").setViewName(
				"playChess");
		registry.addViewController("/uncaughtException");
		registry.addViewController("/resourceNotFound");
		registry.addViewController("/dataAccessFailure");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) { // done
		registry.addResourceHandler("/**").addResourceLocations("/")
				.setCachePeriod(31556926);
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public ReCaptcha reCaptcha() {

		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();

		reCaptcha.setPublicKey(publicKey);
		reCaptcha.setPrivateKey(privateKey);

		return reCaptcha;
	}

	// locale change

	// In Spring MVC application, if you do not configure the Spring’s
	// LocaleResolver,
	// it will use the default AcceptHeaderLocaleResolver, which does not allow
	// to change the locale. To solve it, try declare a SessionLocaleResolver
	// bean
	// in the Spring bean configuration file, it should be suits in most cases
	@Bean
	public SessionLocaleResolver localeResolver() {

		Locale defaultLocale = new Locale("en");

		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(defaultLocale);

		return slr;
	}

	// theme change

	// Resolves localized <theme_name>.properties files in the classpath to
	// allow for theme support
	@Bean
	public ResourceBundleThemeSource themeSource() {

		ResourceBundleThemeSource rbts = new ResourceBundleThemeSource();
		rbts.setBasenamePrefix("theme-");

		return rbts;
	}

	// Store preferred theme configuration in a cookie
	@Bean
	public CookieThemeResolver themeResolver() {

		CookieThemeResolver ctr = new CookieThemeResolver();
		ctr.setCookieName("theme");
		ctr.setDefaultThemeName("default");

		return ctr;
	}

	@Bean
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".html");
		// NB, selecting HTML5 as the template mode.
		resolver.setTemplateMode("HTML5");
		resolver.setCacheable(false);
		return resolver;

	}

	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver());
		engine.addDialect(new SpringSecurityDialect());
		return engine;
	}

	@Bean
	public ViewResolver viewResolver() {

		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setOrder(1);
		viewResolver.setViewNames(new String[] { "game" });
		viewResolver.setCache(false);
		viewResolver.setOrder(2);

		return viewResolver;
	}

	@Bean
	public ViewResolver tilesViewResolver() {

		UrlBasedViewResolver ubvr = new UrlBasedViewResolver();
		ubvr.setViewClass(TilesView.class);

		ubvr.setOrder(1);

		return ubvr;
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {

		String[] definitions = new String[] { "/layouts/layouts.xml",
				"/views/**/views.xml" };
		TilesConfigurer tc = new TilesConfigurer();
		tc.setDefinitions(definitions);

		return tc;
	}
}
