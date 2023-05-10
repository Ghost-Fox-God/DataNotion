package com.datanotion.backend.repositories;

import org.springframework.core.io.Resource;

public interface IFileRepository {

  public String save(byte[] bytes, String filename);

  public Resource load(String filename);
}
