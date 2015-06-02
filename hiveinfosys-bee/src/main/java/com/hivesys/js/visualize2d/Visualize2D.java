/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.js.visualize2d;

import com.hivesys.js.ThreejsState;
import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

/**
 *
 * @author swoorup
 */
@JavaScript({"three.min.js", "OrbitControls.js", "visualize2d-connector.js", "visualize2d.js"})

public final class Visualize2D extends AbstractJavaScriptComponent {

  public Visualize2D() {
  }

  @Override
  protected Visualize2DState getState() {
    return (Visualize2DState) super.getState();
  }
}
