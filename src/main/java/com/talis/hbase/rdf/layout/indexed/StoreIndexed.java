/*
 * Copyright © 2010, 2011, 2012 Talis Systems Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.talis.hbase.rdf.layout.indexed;

import com.talis.hbase.rdf.StoreDesc;
import com.talis.hbase.rdf.connection.HBaseRdfConnection;
import com.talis.hbase.rdf.layout.LoaderTuplesNodes;
import com.talis.hbase.rdf.layout.QueryRunnerBase;

public class StoreIndexed extends StoreBaseIndexed
{
    public StoreIndexed( HBaseRdfConnection connection, StoreDesc desc )
    {
        super( connection, desc, new QueryRunnerBase( desc.getStoreName(), connection, QueryRunnerIndexed.class ), 
        	   new FmtLayoutIndexed( desc.getStoreName(), connection ), 
        	   new LoaderTuplesNodes( desc.getStoreName(), connection, TupleLoaderIndexed.class ) ) ;
        ( (LoaderTuplesNodes) this.getLoader() ).setStore( this ) ;
    }    
}
