/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.iotdb.db.timeIndex.device;

import org.apache.iotdb.db.engine.storagegroup.TsFileResource;
import org.apache.iotdb.db.metadata.PartialPath;
import org.apache.iotdb.tsfile.read.filter.basic.Filter;

import java.util.List;
import java.util.Map;

public class RocksDBDeviceTimeIndexer implements DeviceTimeIndexer {
  @Override
  public boolean init() {
    return false;
  }

  @Override
  public boolean begin() {
    return false;
  }

  @Override
  public boolean end() {
    return false;
  }

  @Override
  public boolean addIndexForDevice(PartialPath deviceId, long startTime, long endTime, String tsFilePath) {
    return false;
  }

  @Override
  public boolean addIndexForDevice(String deviceId, long startTime, long endTime, String tsFilePath) {
    return false;
  }

  @Override
  public boolean addIndexForDevices(Map<String, Integer> deviceIds, long[] startTimes, long[] endTimes, String tsFilePath) {
    return false;
  }

  @Override
  public boolean deleteIndexForDevice(PartialPath deviceId, long startTime, long endTime, String tsFilePath) {
    return false;
  }

  @Override
  public boolean deleteIndexForDevice(String deviceId, long startTime, long endTime, String tsFilePath) {
    return false;
  }

  @Override
  public boolean deleteIndexForDevices(Map<String, Integer> deviceIds, long[] startTimes, long[] endTimes, String tsFilePath) {
    return false;
  }

  @Override
  public boolean updateIndexForDevices(UpdateIndexsParam updateIndexsParam) {
    return false;
  }

  @Override
  public List<TsFileResource> filterByOneDevice(PartialPath deviceId, Filter timeFilter) {
    return null;
  }
}
