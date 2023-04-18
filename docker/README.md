## Preparation
[Source](https://www.elastic.co/guide/en/elasticsearch/reference/current/vm-max-map-count.html)

Before you can run this on a mac with podman: 

```
$ podman machine ssh
Connecting to vm podman-machine-default. To close connection, use `~.` or `exit`
Fedora CoreOS 37.20221106.2.1
Tracker: https://github.com/coreos/fedora-coreos-tracker
Discuss: https://discussion.fedoraproject.org/tag/coreos

[root@localhost ~]# sysctl -w vm.max_map_count=262144
/proc/sys/vm/max_map_count = 262144
[root@localhost ~]#
logout
Connection to localhost closed.
```

On a regular ubuntu machine you have to run the same as root (or via sudo)
```
sysctl -w vm.max_map_count=262144
```

## Prepare your env
cp  example-.env  .env

## Starting with podman-compose
podman-compose up

## Access kibana
http://localhost:5601 ,  username is elastic,  password is the specified value in the .env file

## Stopping with podman-compose
podman-compose down


## Alternatively, start and stop with docker-compose
docker-compose up

.... do your things

docker-compose down
