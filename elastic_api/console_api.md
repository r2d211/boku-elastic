
# create life cycle policy

PUT _ilm/policy/boku_policy
{
  "policy": {
    "phases": {
      "hot": {
        "min_age": "0ms",
        "actions": {
          "rollover": {
            "max_age": "45d"
          }
        }
      },
      "cold": {
        "min_age": "180d",
        "actions": {
          "set_priority": {
            "priority": 0
          }
        }
      }
    }
  }
}


# create template

PUT _index_template/boku_template
{
  "index_patterns": ["some-*"], 
  "template": {
    "settings": {
      "number_of_shards": 1,
      "number_of_replicas": 1,
      "index.lifecycle.name": "boku_policy", 
      "index.lifecycle.rollover_alias": "alias-some-index" 
    }
  }
}

# init template

PUT some-index-init0
{
  "aliases": {
    "alias-some-index":{
      "is_write_index": true 
    }
  }
}


# create index
PUT some-index
{
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 1,
    "index.lifecycle.name": "boku_policy" 
  },
  "mappings": {
    "properties": {
      "status": { "type": "keyword" },
      "qualifier": { "type": "keyword" }
    }
  }
}



# create alias

 POST _aliases
{
  "actions": [
    {
      "add": {
        "index": "some-index",
        "alias": "alias-some-idnex"
      }
    }
  ]
}

# apply transform

PUT _transform/boku_compaction
{
  "source": {
    "index": "some-index"
  },
  "pivot": {
    "group_by": {
      "timestamp": {
        "date_histogram": {
          "field": "timestamp",
          "calendar_interval": "1d"
        }
      },
      "qualifier": {
        "terms": {
          "field": "qualifier"
        }
      },
      "status": {
        "terms": {
          "field": "status"
        }
      }
    },
    "aggregations": {
      "uuid.value_count": {
        "value_count": {
          "field": "uuid.keyword"
        }
      }
    }
  },
  "description": "Compacted information about quantity of logs preserving status and qualifier per 1 day",
  "dest": {
    "index": "compaction"
  },
  "frequency": "1h",
  "retention_policy": {
    "time": {
      "field": "timestamp",
      "max_age": "180d"
    }
  }
}

