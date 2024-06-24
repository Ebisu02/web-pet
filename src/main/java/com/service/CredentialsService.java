package com.service;

import com.model.Credentials;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.servlet.ServletException;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

public class CredentialsService {
    public static Credentials getCredentials(StringTokenizer stringTokenizer, String encodedCredentials) throws ServletException, UnsupportedEncodingException {
        if (!stringTokenizer.hasMoreTokens()) {
            throw new RuntimeException("StringTokenizer has only 1 token");
        }
        String basicHeader = stringTokenizer.nextToken();
        if (!basicHeader.equalsIgnoreCase("Basic")) {
            throw new ServletException("BasicAuthHeader isnt equal case 'Basic'");
        }
        encodedCredentials = stringTokenizer.nextToken();
        if (encodedCredentials.isEmpty()) {
            throw new RuntimeException("Encoded credentials is empty");
        }
        String stringCredentials = new String(Base64.decode(encodedCredentials), "UTF-8");
        int p = stringCredentials.indexOf(":");
        if (p == -1) {
            throw new RuntimeException("Invalid basic header: stringCred.indexOf(':')==-1");
        }
        String name = stringCredentials.substring(0, p).trim();
        String pass = stringCredentials.substring(p + 1).trim();
        if (name.isEmpty() && pass.isEmpty()) {
            throw new RuntimeException("Empty username or user-password strings");
        }
        Credentials credentials = new Credentials(name, pass);
        return credentials;
    }
}
