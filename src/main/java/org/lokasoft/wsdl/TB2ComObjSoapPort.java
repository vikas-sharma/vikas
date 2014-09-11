/**
 * TB2ComObjSoapPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.lokasoft.wsdl;

public interface TB2ComObjSoapPort extends java.rmi.Remote {
    public java.lang.String doMove(java.lang.String fen, java.lang.String move) throws java.rmi.RemoteException;
    public java.lang.String getBestMoves(java.lang.String fen) throws java.rmi.RemoteException;
    public java.lang.String probePosition(java.lang.String fen) throws java.rmi.RemoteException;
}
