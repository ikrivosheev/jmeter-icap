package org.apache.jmeter.protocol.icap.sampler.gui;


import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.protocol.icap.sampler.ICAPSampler;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledChoice;
import org.apache.jorphan.gui.JLabeledTextField;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;


public class ICAPSamplerGui extends AbstractSamplerGui {
    private static Logger logger = LogManager.getLogger(ICAPSampler.class);

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
            sampler.setHost(domain.getText());
            sampler.setPort(port.getText());
//            sampler.setService(service.getText());
        }
    }

    @Override
    public void configure(TestElement el) {
        super.configure(el);

        if (el instanceof ICAPSampler) {
            ICAPSampler sampler = (ICAPSampler) el;
            domain.setText(sampler.getHost());
            port.setText(Integer.toString(sampler.getPort()));
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

    }

    private void initFields() {
        domain.setText("");
        port.setText("");
        connectTimeout.setText("");
        responseTimeout.setText("");
    }

}
