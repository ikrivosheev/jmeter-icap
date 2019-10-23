package org.apache.jmeter.protocol.icap.sampler.gui;


import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.protocol.icap.sampler.ICAPSampler;
import org.apache.jmeter.protocol.icap.sampler.util.ICAPConstatnts;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledChoice;
import org.apache.jorphan.gui.JLabeledTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;


public class ICAPSamplerGui extends AbstractSamplerGui {
    // ICAP host
    private JLabeledTextField domain;

    // ICAP port
    private JLabeledTextField port;

    // ICAP service
    private JLabeledTextField service;

    // ICAP method
    private JLabeledChoice method;

    private JLabeledTextField connectTimeout;
    private JLabeledTextField responseTimeout;

    private JLabeledTextField body;
    private JButton browseButton;
    private JFileChooser bodyFileChooser;

    public ICAPSamplerGui() {
        init();
        initFields();
    }

    @Override
    public TestElement createTestElement() {
        ICAPSampler sampler = new ICAPSampler();
        modifyTestElement(sampler);
        return sampler;
    }

    @Override
    public void modifyTestElement(TestElement el) {
        super.configureTestElement(el);

        if (el instanceof ICAPSampler) {
            ICAPSampler sampler = (ICAPSampler) el;
            sampler.setMethod(method.getText());
            sampler.setHost(domain.getText());
            sampler.setPort(port.getText());
            sampler.setService(service.getText());
            sampler.setConnectTimeout(connectTimeout.getText());
            sampler.setReadTimeout(responseTimeout.getText());
            sampler.setBodyFile(body.getText());
        }
    }

    @Override
    public void configure(TestElement el) {
        super.configure(el);

        if (el instanceof ICAPSampler) {
            ICAPSampler sampler = (ICAPSampler) el;
            domain.setText(sampler.getHost());
            port.setText(sampler.getPort());
            service.setText(sampler.getService());
            connectTimeout.setText(sampler.getConnectTimeout());
            responseTimeout.setText(sampler.getReadTimeout());
            body.setText(sampler.getBodyFile());
        }
    }

    @Override
    public String getStaticLabel() {
        return "ICAP Sampler";
    }

    @Override
    public String getLabelResource() {  return getClass().getCanonicalName(); }

    private void init() {
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());

        JPanel mainPanel = new VerticalPanel();
        mainPanel.add(getConnectPanel());
        mainPanel.add(getRequestPanel());

        JPanel container = new JPanel(new BorderLayout());
        container.add(mainPanel, BorderLayout.NORTH);

        add(makeTitlePanel(), BorderLayout.NORTH);
        add(container, BorderLayout.CENTER);
    }

    private Component getConnectPanel() {
        JPanel connect = new HorizontalPanel();
        connect.add(getICAPServerPanel());
        connect.add(getTimeoutPanel());
        return connect;
    }

    private Component getICAPServerPanel() {
        domain = new JLabeledTextField("Server name or IP: ", 7);
        port = new JLabeledTextField("Port: ", 7);

        JPanel icapServerPanel = new HorizontalPanel();
        icapServerPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "ICAP Server"
        ));
        icapServerPanel.add(domain);
        icapServerPanel.add(port);
        return icapServerPanel;
    }

    private Component getTimeoutPanel() {
        connectTimeout = new JLabeledTextField("Connect: ", 7);
        responseTimeout = new JLabeledTextField("Response: ", 7);

        JPanel timeOut = new HorizontalPanel();
        timeOut.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Timeouts (milliseconds)"
        )); // $NON-NLS-1$

        timeOut.add(connectTimeout);
        timeOut.add(responseTimeout);
        return timeOut;
    }

    private Component getRequestPanel() {
        method = new JLabeledChoice("Method: ", ICAPConstatnts.METHODS, true, false);
        service = new JLabeledTextField("Service: ");
        body = new JLabeledTextField("Body: ");
        browseButton = new JButton("Browse");
        bodyFileChooser = new JFileChooser();
        bodyFileChooser.addActionListener(this::attachmentFolderFileChooserActionPerformed);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;

        JPanel panelRequest = new JPanel(new GridBagLayout());

        panelRequest.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "ICAP Request"
        ));

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panelRequest.add(method, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panelRequest.add(service, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panelRequest.add(body, gridBagConstraints);

        browseButton.addActionListener(this::browseButtonActionPerformed);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        panelRequest.add(browseButton, gridBagConstraints);

        return panelRequest;
    }

    private void browseButtonActionPerformed(ActionEvent evt) { // NOSONAR This method is used through lambda
        bodyFileChooser.showOpenDialog(this);
    }

    private void attachmentFolderFileChooserActionPerformed(ActionEvent evt) { // NOSONAR This method is used through lambda
        File chosen = bodyFileChooser.getSelectedFile();
        if (chosen == null) {
            return;
        }
        body.setText(chosen.getAbsolutePath());
    }

    private void initFields() {
        domain.setText("");
        port.setText("");
        connectTimeout.setText("");
        responseTimeout.setText("");
        method.setText("");
        service.setText("");
        body.setText("");
    }

}

