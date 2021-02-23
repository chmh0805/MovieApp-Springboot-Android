package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.domain.MovieRepository;
import com.example.demo.domain.Yts;
import com.google.gson.Gson;

@RestController
public class MovieDownloadController {

	@Autowired
	private MovieRepository movieRepository;
	
	@GetMapping({"", "/"})
	public String home() {
		return "home";
	}
	
	@GetMapping("/movie/download/{page}")
	public String download(@PathVariable int page) {
		RestTemplate rt = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
				" AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

		HttpEntity request = 
				new HttpEntity(headers);

		ResponseEntity response = rt.exchange(
				"https://yts.mx/api/v2/list_movies.json?limit=50&page="+page, 
				HttpMethod.GET, 
				request, 
				String.class
		);

		System.out.println("response getBody() : "+response.getBody());

		Gson gson = new Gson();
		Yts yts = gson.fromJson(response.getBody().toString(), Yts.class);

		movieRepository.saveAll(yts.getData().getMovies());
		System.out.println("yts : "+yts);
	
		return "다운로드 완료";
	}
}
