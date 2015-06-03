/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.javascript;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

/**
 *
 * @author swoorup
 */

@JavaScript({"three.min.js", "OrbitControls.js", "connector.js", "myscene.js"})

public class Threejs extends AbstractJavaScriptComponent {

  public Threejs() {
    getState().xhtml = "";
  }

  @Override
  protected ThreejsState getState() {
    return (ThreejsState) super.getState();
  }
}
