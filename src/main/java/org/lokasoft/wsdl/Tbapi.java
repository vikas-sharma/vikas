/**
 * Tbapi.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.lokasoft.wsdl;

public interface Tbapi extends javax.xml.rpc.Service {
    public java.lang.String getTB2ComObjSoapPortAddress();

    public org.lokasoft.wsdl.TB2ComObjSoapPort getTB2ComObjSoapPort() throws javax.xml.rpc.ServiceException;

    public org.lokasoft.wsdl.TB2ComObjSoapPort getTB2ComObjSoapPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
