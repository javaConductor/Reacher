package com.example.demo.jfx2.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiscogsFormat {
  String name;
  String qty;
  List<String> descriptions;
  String text;
}
