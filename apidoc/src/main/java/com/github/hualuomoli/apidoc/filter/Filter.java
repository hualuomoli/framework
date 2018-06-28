package com.github.hualuomoli.apidoc.filter;

import java.io.File;

public interface Filter {

  boolean support(String packageName, File file);

}
