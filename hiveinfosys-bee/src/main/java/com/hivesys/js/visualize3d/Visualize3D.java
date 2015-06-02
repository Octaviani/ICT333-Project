package com.hivesys.js.visualize3d;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

/**
 *
 * @author swoorup
 */
@JavaScript({"three.min.js", "OrbitControls.js", "visualize3d-connector.js", "visualize3d.js"})

public final class Visualize3D extends AbstractJavaScriptComponent {

  public Visualize3D() {
  }

  @Override
  protected Visualize3DState getState() {
    return (Visualize3DState) super.getState();
  }
}
