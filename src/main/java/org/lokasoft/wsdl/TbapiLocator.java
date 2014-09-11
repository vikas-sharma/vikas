/**
 * TbapiLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.lokasoft.wsdl;

public class TbapiLocator extends org.apache.axis.client.Service implements org.lokasoft.wsdl.Tbapi {

    public TbapiLocator() {
    }


    public TbapiLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TbapiLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TB2ComObjSoapPort
    private java.lang.String TB2ComObjSoapPort_address = "http://www.lokasoft.nl/tbweb/tbapi.asp";

    public java.lang.String getTB2ComObjSoapPortAddress() {
        return TB2ComObjSoapPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TB2ComObjSoapPortWSDDServiceName = "TB2ComObjSoapPort";

    public java.lang.String getTB2ComObjSoapPortWSDDServiceName() {
        return TB2ComObjSoapPortWSDDServiceName;
    }

    public void setTB2ComObjSoapPortWSDDServiceName(java.lang.String name) {
        TB2ComObjSoapPortWSDDServiceName = name;
    }

    public org.lokasoft.wsdl.TB2ComObjSoapPort getTB2ComObjSoapPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TB2ComObjSoapPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTB2ComObjSoapPort(endpoint);
    }

    public org.lokasoft.wsdl.TB2ComObjSoapPort getTB2ComObjSoapPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.lokasoft.wsdl.TB2ComObjSoapBindingStub _stub = new org.lokasoft.wsdl.TB2ComObjSoapBindingStub(portAddress, this);
            _stub.setPortName(getTB2ComObjSoapPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTB2ComObjSoapPortEndpointAddress(java.lang.String address) {
        TB2ComObjSoapPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.lokasoft.wsdl.TB2ComObjSoapPort.class.isAssignableFrom(serviceEndpointInterface)) {
                org.lokasoft.wsdl.TB2ComObjSoapBindingStub _stub = new org.lokasoft.wsdl.TB2ComObjSoapBindingStub(new java.net.URL(TB2ComObjSoapPort_address), this);
                _stub.setPortName(getTB2ComObjSoapPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("TB2ComObjSoapPort".equals(inputPortName)) {
            return getTB2ComObjSoapPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://lokasoft.org/wsdl/", "tbapi");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://lokasoft.org/wsdl/", "TB2ComObjSoapPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TB2ComObjSoapPort".equals(portName)) {
            setTB2ComObjSoapPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
