package com.example.demo.repository;

import com.example.demo.models.SimpleModel;
import org.springframework.data.repository.CrudRepository;

public interface PostgresRepository extends CrudRepository<SimpleModel, Integer> {

}
