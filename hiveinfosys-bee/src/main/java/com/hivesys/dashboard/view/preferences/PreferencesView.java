package com.hivesys.dashboard.view.preferences;

/*
 * Copyright 2000-2013 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
import com.hivesys.dashboard.domain.User;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @since @author Vaadin Ltd
 */
public class PreferencesView extends VerticalLayout implements View {

    private final BeanFieldGroup<User> fieldGroup;
    
    @PropertyId("firstName")
    private final TextField firstNameField;
    @PropertyId("lastName")
    private final TextField lastNameField;
    @PropertyId("title")
    private final ComboBox titleField;
    @PropertyId("username")
    private final TextField usernameField;
    @PropertyId("male")
    private final OptionGroup sexField;
    @PropertyId("email")
    private final TextField emailField;
    @PropertyId("location")
    private final TextField locationField;
    @PropertyId("phone")
    private final TextField phoneField;
    @PropertyId("website")
    private final TextField websiteField;
    @PropertyId("bio")
    private final RichTextArea bioField;
    
    final User user;

    public PreferencesView() {

        user = (User) VaadinSession.getCurrent().getAttribute(
                User.class.getName());
        
        setSpacing(true);
        setMargin(true);

        Label title = new Label("Forms");
        title.addStyleName("h1");
        addComponent(title);

        final FormLayout form = new FormLayout();
        form.setMargin(false);
        form.setWidth("800px");
        form.addStyleName("light");
        addComponent(form);

        Label section = new Label("Personal Info");
        section.addStyleName("h2");
        section.addStyleName("colored");
        form.addComponent(section);

        firstNameField = new TextField("First Name");
        firstNameField.setWidth("50%");
        form.addComponent(firstNameField);

        lastNameField = new TextField("Last Name");
        lastNameField.setWidth("50%");
        form.addComponent(lastNameField);

        titleField = new ComboBox("Title");
        titleField.setInputPrompt("Please specify");
        titleField.addItem("Mr.");
        titleField.addItem("Mrs.");
        titleField.addItem("Ms.");
        titleField.setNewItemsAllowed(true);
        form.addComponent(titleField);

        usernameField = new TextField("Username");
        usernameField.setRequired(true);
        form.addComponent(usernameField);

        sexField = new OptionGroup("Sex");
        sexField.addItem(Boolean.FALSE);
        sexField.setItemCaption(Boolean.FALSE, "Female");
        sexField.addItem(Boolean.TRUE);
        sexField.setItemCaption(Boolean.TRUE, "Male");
        sexField.addStyleName("horizontal");
        form.addComponent(sexField);

        section = new Label("Contact Info");
        section.addStyleName("h3");
        section.addStyleName("colored");
        form.addComponent(section);

        emailField = new TextField("Email");
        emailField.setWidth("50%");
        emailField.setRequired(true);
        form.addComponent(emailField);

        locationField = new TextField("Location");
        locationField.setWidth("50%");
        locationField.setNullRepresentation("");
        form.addComponent(locationField);

        phoneField = new TextField("Phone");
        phoneField.setWidth("50%");
        phoneField.setNullRepresentation("");
        form.addComponent(phoneField);

        section = new Label("Additional Info");
        section.addStyleName("h4");
        section.addStyleName("colored");
        form.addComponent(section);

        websiteField = new TextField("Website");
        websiteField.setInputPrompt("http://");
        websiteField.setWidth("100%");
        form.addComponent(websiteField);

        bioField = new RichTextArea("Bio");
        bioField.setWidth("100%");
        bioField.setValue("<div><p><span>Integer legentibus erat a ante historiarum dapibus.</span> <span>Vivamus sagittis lacus vel augue laoreet rutrum faucibus.</span> <span>A communi observantia non est recedendum.</span> <span>Morbi fringilla convallis sapien, id pulvinar odio volutpat.</span> <span>Ab illo tempore, ab est sed immemorabili.</span> <span>Quam temere in vitiis, legem sancimus haerentia.</span></p><p><span>Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</span> <span>Quam diu etiam furor iste tuus nos eludet?</span> <span>Cum sociis natoque penatibus et magnis dis parturient.</span> <span>Quam diu etiam furor iste tuus nos eludet?</span> <span>Tityre, tu patulae recubans sub tegmine fagi  dolor.</span></p><p><span>Curabitur blandit tempus ardua ridiculus sed magna.</span> <span>Phasellus laoreet lorem vel dolor tempus vehicula.</span> <span>Etiam habebis sem dicantur magna mollis euismod.</span> <span>Hi omnes lingua, institutis, legibus inter se differunt.</span></p></div>");
        form.addComponent(bioField);

        Button edit = new Button("Edit", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean readOnly = form.isReadOnly();
                if (readOnly) {
                    bioField.setReadOnly(false);
                    form.setReadOnly(false);
                    form.removeStyleName("light");
                    event.getButton().setCaption("Save");
                    event.getButton().addStyleName("primary");
                } else {
                    bioField.setReadOnly(true);
                    form.setReadOnly(true);
                    form.addStyleName("light");
                    event.getButton().setCaption("Edit");
                    event.getButton().removeStyleName("primary");
                }
            }
        });

        HorizontalLayout footer = new HorizontalLayout();
        footer.setMargin(new MarginInfo(true, false, true, false));
        footer.setSpacing(true);
        footer.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        form.addComponent(footer);
        footer.addComponent(edit);

        Label lastModified = new Label("Last modified by you a minute ago");
        lastModified.addStyleName("light");
        footer.addComponent(lastModified);
        
        fieldGroup = new BeanFieldGroup<>(User.class);
        fieldGroup.bindMemberFields(this);
        fieldGroup.setItemDataSource(user);
        
        form.setReadOnly(true);
        bioField.setReadOnly(true);
        
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }
}
