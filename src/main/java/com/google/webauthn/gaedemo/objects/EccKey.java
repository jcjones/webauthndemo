// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.webauthn.gaedemo.objects;

import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.CborException;
import co.nstant.in.cbor.model.ByteString;
import co.nstant.in.cbor.model.Map;
import co.nstant.in.cbor.model.UnicodeString;
import com.googlecode.objectify.annotation.Subclass;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Subclass
public class EccKey extends CredentialPublicKey {
  byte[] x, y;

  EccKey() {
    x = null;
    y = null;
    alg = Algorithm.UNDEFINED;
  }

  /**
   * @param x
   * @param y
   */
  public EccKey(byte[] x, byte[] y) {
    super();
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object obj) {
    try {
      if (obj instanceof EccKey) {
        EccKey other = (EccKey) obj;
        if (Arrays.equals(x, other.x) && Arrays.equals(y, other.y) && alg == other.alg) {
          return true;
        }
      }
    } catch (NullPointerException e) {
    }
    return false;
  }

  @Override
  public byte[] encode() throws CborException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    Map map = new Map();
    map.put(new UnicodeString("alg"), new UnicodeString(alg.toString()));
    map.put(new UnicodeString("x"), new ByteString(x));
    map.put(new UnicodeString("y"), new ByteString(y));
    List<co.nstant.in.cbor.model.DataItem> dataItems =
        new ArrayList<co.nstant.in.cbor.model.DataItem>();
    dataItems.add(map);

    new CborEncoder(output).encode(dataItems);
    return output.toByteArray();
  }

  /**
   * @return the x
   */
  public byte[] getX() {
    return x;
  }

  /**
   * @return the y
   */
  public byte[] getY() {
    return y;
  }

}
