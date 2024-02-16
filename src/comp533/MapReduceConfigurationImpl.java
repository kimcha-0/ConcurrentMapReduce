package comp533;


import comp533.count.TokenCountingMapper;
import comp533.factory.BarrierImpl;
import comp533.factory.JoinerImpl;
import comp533.factory.MapperFactory;
import comp533.factory.PartitionerFactory;
import comp533.factory.PartitionerImpl;
import comp533.factory.ReducerFactory;
import comp533.factory.TokenCountingReducer;
import comp533.mvc.ModelImpl;
import comp533.mvc.ControllerImpl;
import comp533.mvc.ViewImpl;
import comp533.sum.IntSummingMapper;
import gradingTools.comp533s21.assignment1.interfaces.MapReduceConfiguration;


public class MapReduceConfigurationImpl implements MapReduceConfiguration {
	public MapReduceConfigurationImpl() {}
	@Override
	public Class getStandAloneTokenCounter() {
		return TokenCounter.class;
	}
	
	@Override
	public Class getStandAloneIntegerSummer() {
		return IntegerSummer.class;
	}
	
	@Override
	public Class getModelClass() {
		return ModelImpl.class;
	}
	
	@Override
	public Class getControllerClass() {
		return ControllerImpl.class;
	}
	
	@Override
	public Class getViewClass() {
		return ViewImpl.class;
	}
	
	@Override
	public Class getMapperFactory() {
		return MapperFactory.class;
	}
	
	@Override
	public Class getReducerFactory() {
		return ReducerFactory.class;
	}
	
	@Override
	public Class getKeyValueClass() {
		return KeyValue.class;
	}

	@Override
    public Class getTokenCountingMapperClass() {
    	return TokenCountingMapper.class;
    }

	@Override
    public Class getIntSummingMapperClass() {
    	// extra credit
    	return IntSummingMapper.class;
    }

	@Override
    public Class getReducerClass() {
    	return TokenCountingReducer.class;
    }

    // Return instances of the required objects, using the relevant factories
    // if they return these objects by default
	@Override
    public Object getTokenCountingMapper() {
    	// default object returned by Mapper factory
    	return MapperFactory.getMapper();
    }

	@Override
    public Object getIntSummingMapper() {
		return IntSummingMapper.getInstance();
    }

	@Override
    public Object getReducer() {
    	// default object returned by Reducer factory   
    	return ReducerFactory.getReducer();
    }

	@Override
	public Class getPartitionerClass() {
		 return PartitionerImpl.class;
	 }

	@Override
	public Class getPartitionerFactory() {
		 return PartitionerFactory.class;
	 }

	@Override
	public Object getPartitioner() {
		 return PartitionerFactory.getPartitioner();
	 }

	@Override
	public Class getJoinerClass() {
		 return JoinerImpl.class;
	 }

	@Override
	public Class getBarrierClass() {
		return BarrierImpl.class;
	}

	 @Override
	 public Class getSlaveClass() {
		 return Slave.class;
	 }

	 // return some instance of the Barrier and Joiner classes in the  methods
	 // below as these are not singletons
	 @Override
	 public Object getBarrier(int aNumThreads) {
		 return new BarrierImpl(aNumThreads);
	 }

	@Override
	public Object getJoiner(int aNumThreads) {
		return new JoinerImpl(aNumThreads);
	}

	    // --------------------A3--------------------------

	    
	    // The model stored in the server will now have a remote interface to be used 
	    // by the client to register its proxy. 
	    // The model class will have to be changed to implement  this interface. 
	    // If you change the name of this class, make sure you change the getModelClass
	    // method of the registry to reflect the name change.   
	@Override
    public Class getRemoteModelInterface() {
    	return null;
    }
	    
	    
	    // The client proxy also has a remote interface for remote callbacks
	@Override
	public Class getRemoteClientObjectInterface() {
    	return null;
    }
		
		// This is the class implementing the interface
	@Override
	public Class getRemoteClientObjectClass() {
		return null;
	}
		
		// For each application (token count, int sum), we now have two main classes,
		// one for the server and one for each client.
			
		// The main class of the server token counter.	
	@Override
	public Class getServerTokenCounter() {
		return null;
	}
	
	@Override
	// The main class of the server int summer.
    public Class getServerIntegerSummer() {
		return null;
    }
	    
    // The main class of both the client token counter and the int summer,
  	// since the reduction step is the same for both, and the client only
    // performs reduction
	@Override
    public Class getClientTokenCounter() {
    	return null;
    }
	    
    // A standalone class similar to the two classes in A2 implementing the Facebook 
    // map reduce algorithm
	@Override
	public Class getStandAloneFacebookMapReduce() {
		return null;
	}

	// The main class of the server facebook implementation
	@Override
	public Class getServerFacebookMapReduce() {
		return null;
	}
	// The main class of the client facebook implementation
	@Override
	public Class getRemoteClientFacebookMapReduce() {
		return null;
	}
}
