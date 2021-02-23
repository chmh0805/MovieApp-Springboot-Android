package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Movie;
import com.example.demo.domain.MovieRepository;

@RestController
@RequestMapping("api/movie")
public class MovieApiController {

	@Autowired
	private MovieRepository movieRepository;
	
	@GetMapping({"","/"})
	public List<Movie> findAll() {
		return movieRepository.findAll();
	}

	@DeleteMapping("/{id}")
	public String deleteById(@PathVariable long id) {
		movieRepository.deleteById(id);
		return "ok";
	}
}
