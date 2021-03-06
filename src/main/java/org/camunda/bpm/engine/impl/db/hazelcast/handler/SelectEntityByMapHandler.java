/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camunda.bpm.engine.impl.db.hazelcast.handler;

import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.db.DbEntity;
import org.camunda.bpm.engine.impl.db.hazelcast.HazelcastPersistenceSession;

/**
 * @author Sebastian Menski
 */
public class SelectEntityByMapHandler extends TypeAwareStatementHandler implements SelectEntityStatementHandler {

  public SelectEntityByMapHandler(Class<? extends DbEntity> type) {
    super(type);
  }

  @SuppressWarnings("unchecked")
  public <T extends DbEntity> T execute(HazelcastPersistenceSession session, Object parameter) {
    Map<String, String> parameterMap = getParameterMap(parameter);
    List<T> entities = (List<T>) new SelectEntitiesByMapHandler(type).execute(session, parameterMap);
    if (entities.size() != 1) {
      throw new ProcessEngineException("Not only one result was found by statement " + this);
    }
    else {
      return entities.iterator().next();
    }
  }

  @SuppressWarnings("unchecked")
  protected Map<String, String> getParameterMap(Object parameter) {
    return (Map<String, String>) parameter;
  }

}
