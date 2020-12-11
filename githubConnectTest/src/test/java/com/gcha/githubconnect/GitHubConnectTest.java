package com.gcha.githubconnect;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHIssue;

public class GitHubConnectTest {

	static GitHubConnect githubConnect;
	
	@Test
	@DisplayName("issues 조회")
	void getIssues() {
		List<GHIssue> issues = githubConnect.getIssues(null);
		assertNotNull(issues);
	}
	
	@Test
	@DisplayName("참석자리스트조회")
	void getParticipationList() {
		githubConnect.getIssues(null);
		Map<String ,Integer> map = githubConnect.getParticipationList();
		assertNotNull(map);
	}
	
	@Test
	@DisplayName("참석자리스트조회")
	void findTotalParticipantsByPercent() {
		githubConnect.getIssues(null);
		System.out.println("====================1");
		Map<String ,Integer> map = githubConnect.getParticipationList();
		System.out.println("====================2");
		String str = githubConnect.findTotalParticipantsByPercent(map);
		System.out.println(str);
		//assertNotNull(map);
	}
	
	@BeforeAll
	static void init() {
		//token은 github에서 입력받으야함.
		String token ="19a15989eb5c36dce617314d0ea11a95146e2f40";
		githubConnect = new GitHubConnect(token);
		
	}
}
