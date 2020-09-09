package com.lyht.base.common.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

import com.lyht.base.common.config.session.LyhtSessionContext;

@Component
public class SessionListener implements HttpSessionListener {

	private LyhtSessionContext sessionContext = LyhtSessionContext.getInstance();

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		sessionContext.addSession(httpSessionEvent.getSession());
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();
		sessionContext.delSession(session);
	}

}
