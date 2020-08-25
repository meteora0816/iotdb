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
package org.apache.iotdb.db.timeIndex;

import org.apache.iotdb.db.conf.IoTDBDescriptor;
import org.apache.iotdb.db.exception.metadata.IllegalPathException;
import org.apache.iotdb.db.metadata.PartialPath;
import org.apache.iotdb.db.timeIndex.device.DeviceTimeIndexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class IndexerManager {
  private String indexerFilePath;
  private Map<PartialPath, DeviceTimeIndexer> seqIndexers;
  private Map<PartialPath, DeviceTimeIndexer> unseqIndexers;
  private ReentrantReadWriteLock lock;
  private static final Logger logger = LoggerFactory.getLogger(IndexerManager.class);

  private static class IndexerManagerHolder {

    private IndexerManagerHolder() {
      // allowed to do nothing
    }

    private static final IndexerManager INSTANCE = new IndexerManager();
  }

  public static IndexerManager getInstance() {
    return IndexerManagerHolder.INSTANCE;
  }

  private IndexerManager() {
    indexerFilePath = IoTDBDescriptor.getInstance().getConfig().getSchemaDir()
      + File.pathSeparator + IndexConstants.INDEXER_FILE;
    seqIndexers = new ConcurrentHashMap<>();
    unseqIndexers = new ConcurrentHashMap<>();
    lock = new ReentrantReadWriteLock();
  }

  /**
   * init all indexer
   * @return whether success
   */
  public boolean init() {
    //TODO
    // 1. get all storage group from file
    // 2. init indexer for the storage group
    return true;
  }

  public void addSeqIndexer(PartialPath storageGroup, DeviceTimeIndexer deviceTimeIndexer) {
    lock.writeLock().lock();
    seqIndexers.put(storageGroup, deviceTimeIndexer);
    lock.writeLock().unlock();
  }

  public void addUnseqIndexer(PartialPath storageGroup, DeviceTimeIndexer deviceTimeIndexer) {
    lock.writeLock().lock();
    unseqIndexers.put(storageGroup, deviceTimeIndexer);
    lock.writeLock().unlock();
  }

  public void deleteSeqIndexer(PartialPath storageGroup) {
    lock.writeLock().lock();
    seqIndexers.remove(storageGroup);
    lock.writeLock().unlock();
  }

  public void deleteUnseqIndexer(PartialPath storageGroup) {
    lock.writeLock().lock();
    unseqIndexers.remove(storageGroup);
    lock.writeLock().unlock();
  }

  public DeviceTimeIndexer getSeqIndexer(PartialPath storageGroup) {
    lock.readLock().lock();
    try {
      return seqIndexers.get(storageGroup);
    } finally {
      lock.readLock().unlock();
    }
  }

  public DeviceTimeIndexer getSeqIndexer(String storageGroup) {
    PartialPath sgName;
    try {
      sgName = new PartialPath(storageGroup);
    } catch (IllegalPathException e) {
      logger.warn("Fail to get DeviceTimeIndexer for storage group {}, err:{}", storageGroup, e.getMessage());
      return null;
    }
    lock.readLock().lock();
    try {
      return seqIndexers.get(sgName);
    } finally {
      lock.readLock().unlock();
    }
  }

  public DeviceTimeIndexer getUnseqIndexer(PartialPath storageGroup) {
    lock.readLock().lock();
    try {
      return unseqIndexers.get(storageGroup);
    } finally {
      lock.readLock().unlock();
    }
  }

  public DeviceTimeIndexer getUnseqIndexer(String storageGroup) {
    PartialPath sgName;
    try {
      sgName = new PartialPath(storageGroup);
    } catch (IllegalPathException e) {
      logger.warn("Fail to get DeviceTimeIndexer for storage group {}, err:{}", storageGroup, e.getMessage());
      return null;
    }
    lock.readLock().lock();
    try {
      return unseqIndexers.get(sgName);
    } finally {
      lock.readLock().unlock();
    }
  }
}
