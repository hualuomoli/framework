package com.github.hualuomoli.sample.gateway.server.biz.type.entity;

public class OutPageResponse {

	private String username;
	private String nickname;
	private Integer age;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "OutPageResponse [username=" + username + ", nickname=" + nickname + ", age=" + age + "]";
	}

}
