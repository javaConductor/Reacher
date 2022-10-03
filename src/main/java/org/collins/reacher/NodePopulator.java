package org.collins.reacher;

import javafx.scene.Node;

public interface NodePopulator<N extends Node, T> {

  Node populate(N node, T data);
}
