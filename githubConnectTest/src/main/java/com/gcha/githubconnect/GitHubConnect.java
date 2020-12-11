package com.gcha.githubconnect;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueComment;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

public class GitHubConnect {


	
	private GitHub github;
	private GHRepository repo;
	private List<GHIssue> issues;
	
	final String REPO ="whiteship/live-study";
//	final String REPO ="gcha-kim/datastructure";
	//
	
	//github에서 발급한 token으로 github 연결 및 repo 접
	GitHubConnect(String token){
		try {
			this.github = new GitHubBuilder().withOAuthToken(token).build();
			this.repo =  github.getRepository(REPO);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//이슈 목록 조회
	public List<GHIssue> getIssues(GHIssueState issueState){
		if(issueState == null) issueState = GHIssueState.ALL;
		
		try {
			issues = repo.getIssues(issueState);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return issues;
	}
	
	//참여자 목록 조회하기
	public Map<String ,Integer> getParticipationList() {
		// 참여자 저장소 생성
        Map<String ,Integer> participantsMap = new HashMap<String,Integer>();
        //issue목
        try {
	        for(int i=0; i < issues.size() ; i++) {
	        	
	        	GHIssue issue = repo.getIssue(i+1);
	        	//issue에대한 Comment 리스트 출력
	        	List<GHIssueComment> issueComments = issue.getComments();
	        	
	        	//issues당 comment 정보 조회
	        	for(GHIssueComment comment : issueComments) {
	        	
	        		String userNm = comment.getUser().getLogin();
	        		if(participantsMap.containsKey(userNm)) {
	        			participantsMap.put(userNm, participantsMap.get(userNm)+1);
	        		}else {
	        			participantsMap.put(userNm, 1);
	        		}
	        	}
	        }
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return participantsMap;
	}
	
	//참여율 출
	public String findTotalParticipantsByPercent(Map<String ,Integer> participantsMap) {
		System.out.println("전체 스터디 횟수 : "+issues.size()+"회");
		StringBuilder strbuilder = new StringBuilder();
		
		participantsMap.forEach((key, value) -> {
			
			double issuzeSize = issues.size();
            double participantsRate = (value/ issuzeSize) * 100;
            
            //소수점 2자리까지 참여율 출력
            strbuilder.append("==> 참여자 ID: "+key+", 참여율 : "+String.format("%.2f", participantsRate)+'%'+'\n');
//			System.out.println("참여자 ID: "+key+", 참여율 : "+String.format("%.2f", participantsRate)+'%');
			
		});
		return strbuilder.toString();
	}
        
}
