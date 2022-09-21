package com.example.demo.jfx2;

import javafx.scene.Node;

public interface NodePopulator<N extends Node, T> {

  Node populate(N node, T data);
}
