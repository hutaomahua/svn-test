package com.lyht.base.common.config.session;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public class LyhtSessionContext {

	private static LyhtSessionContext sessionContext;
	private HashMap<String, HttpSession> sessionMap;

	private LyhtSessionContext() {
		sessionMap = new HashMap<>();
	}

	public static LyhtSessionContext getInstance() {
		if (sessionContext == null) {
			sessionContext = new LyhtSessionContext();
		}
		return sessionContext;
	}

	public synchronized void addSession(HttpSession session) {
		if (session != null) {
			String id = session.getId();
			sessionMap.put(id, session);
		}
	}

	public synchronized void delSession(HttpSession session) {
		if (session != null) {
			String id = session.getId();
			sessionMap.remove(id);
		}
	}

	public synchronized HttpSession getSession(String session_id) {
		if (session_id == null)
			return null;
		HttpSession httpSession = sessionMap.get(session_id);
		return httpSession;
	}

}
