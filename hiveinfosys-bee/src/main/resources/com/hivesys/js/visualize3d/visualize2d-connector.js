window.com_hivesys_js_Visualize3D_js =
function() {
    // Create the component
    var mycomponent =
        new myscene.Sample(this);

    // Handle changes from the server-side
    this.onStateChange = function() {
        mycomponent.setValue(this.getState().value);
    };

    // Pass user interaction to the server-side
    var self = this;
    mycomponent.click = function() {
        self.onClick(mycomponent.getValue());
    };
};
