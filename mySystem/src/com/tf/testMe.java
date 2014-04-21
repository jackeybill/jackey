package com.tf;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hp.ucmdb.adapters.push.env.PushDataAdapterEnvironment;
import com.hp.ucmdb.adapters.push.output.MappingQuery;
import com.hp.ucmdb.adapters.push.output.PushAdapterConnector;
import com.hp.ucmdb.adapters.push.output.PushConnectorInput;
import com.hp.ucmdb.adapters.push.output.ResultTreeNode;
import com.hp.ucmdb.adapters.push.output.ResultTreeNodeActionData;
import com.hp.ucmdb.adapters.push.output.TypeValue;
import com.hp.ucmdb.federationspi.adapter.environment.DataAdapterEnvironment;
import com.hp.ucmdb.federationspi.config.CustomerInformation;
import com.hp.ucmdb.federationspi.data.query.topology.QueryDefinition;
import com.hp.ucmdb.federationspi.data.query.types.ExternalCiId;
import com.hp.ucmdb.federationspi.data.query.types.ExternalRelationId;
import com.hp.ucmdb.federationspi.data.query.types.Relation;
import com.hp.ucmdb.federationspi.data.replication.ReplicationActionDataFactory;
import com.hp.ucmdb.federationspi.data.replication.UpdateResult;
import com.hp.ucmdb.federationspi.data.resource.LocateGeneralResourcesInput;
import com.hp.ucmdb.federationspi.data.resource.LocateGeneralResourcesOutput;
import com.hp.ucmdb.federationspi.data.resource.LocatePushQueriesResourcesInput;
import com.hp.ucmdb.federationspi.data.resource.LocatePushQueriesResourcesOutput;
import com.hp.ucmdb.federationspi.exception.DataAccessException;

public class testMe implements PushAdapterConnector {
	
	
	private PushDataAdapterEnvironment _env;

	@Override
	public void start(PushDataAdapterEnvironment env) throws DataAccessException {
		// TODO Auto-generated method stub
		Relation r;
		//r.getId().
		QueryDefinition qd;
		GroovyTraveller gt;
		
		//qd.g
		this._env = env;
		_env.getLogger().debug("Starting SM Generic Groovy Pusher!!!" );
	}
	
	@Override
	public void testConnection(PushDataAdapterEnvironment env)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void locateGeneralResources(DataAdapterEnvironment env,
			LocateGeneralResourcesInput inputResource, LocateGeneralResourcesOutput outputResource) {
		// TODO Auto-generated method stub
		_env.getLogger().debug("Begin to locateGeneralResource !!!");
		CustomerInformation info = null;
		info.getCustomerID();		
	}
	
	@Override
	public void locatePushQueriesResources(DataAdapterEnvironment env,
			LocatePushQueriesResourcesInput inputQueryResource,
			LocatePushQueriesResourcesOutput outputQueryResource) {
		// TODO Auto-generated method stub
		_env.getLogger().debug("Begin to locatePushResource !!!");
		for (String queryName : inputQueryResource.getQueryNames()) {
			_env.getLogger().debug("Query name: "+ queryName);
		}
		
	}
	
	@Override
	public UpdateResult pushTreeNodes(PushConnectorInput input) throws DataAccessException {

		_env.getLogger().debug("Enter pushTreeNodes! ");
		try {
			Map<ExternalCiId, ExternalCiId> ciMapping = new HashMap<ExternalCiId, ExternalCiId>();
			Map<ExternalRelationId, ExternalRelationId> relationMapping = new HashMap<ExternalRelationId, ExternalRelationId>();
			UpdateResult updateResult = ReplicationActionDataFactory.createUpdateResult(ciMapping, relationMapping);
			ResultTreeNodeActionData resultTreeNodeActionData = input.getResultTreeNodes();
			input.getQueryDefinition();			
			List<ResultTreeNode> dataToAdd = resultTreeNodeActionData.getDataToAdd();
			
			int cnt = 0;
			for (ResultTreeNode node: dataToAdd){
				for ( Map.Entry<String, List<ResultTreeNode>> map: node.getAllChildren().entrySet() ){
					_env.getLogger().debug("==== child [" + map.getKey() + "]");
					for ( ResultTreeNode n: dataToAdd){
						for ( Map.Entry<String, TypeValue> mp: n.getAllProperties().entrySet() ) {
							_env.getLogger().debug("==== attribute [" + mp.getKey() + "], type=["
									+ mp.getValue().getType() + "], value=[" + mp.getValue().getValue() + "]");
						}
					}
				};
				_env.getLogger().debug("TreeNode [" + ++cnt +"]: " + node.getQueryNodeName()+":");
				for ( Map.Entry<String, TypeValue> m: node.getAllProperties().entrySet() ) {
					_env.getLogger().debug("==== attribute [" + m.getKey() + "], type=["
							+ m.getValue().getType() + "], value=[" + m.getValue().getValue() + "]");
				}
			}
			List<ResultTreeNode> dataToUpdate = resultTreeNodeActionData.getDataToUpdate();
			List<ResultTreeNode> dataToRemove = resultTreeNodeActionData.getDataToRemove();
			return updateResult;
		} catch (RuntimeException e) {
			_env.getLogger().error(e, "Fatal Exception during push");
			throw e;
		}
	}
	
	@Override
	public Map<Object, Set<Object>> retrieveNonUniqueMapping(MappingQuery arg0) {
		// TODO Auto-generated method stub
		_env.getLogger().debug("Enter retrieveNonUniqueMapping .. ");
		return null;
	}
	
	@Override
	public Map<Object, Object> retrieveUniqueMapping(MappingQuery arg0) {
		// TODO Auto-generated method stub
		_env.getLogger().debug("Enter retrieveUniqueMapping .. ");
		return null;
	}
}
