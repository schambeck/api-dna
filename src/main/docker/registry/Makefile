docker-run:
	docker run -d \
		-p 5000:5000 \
		--restart=always \
		--name registry registry:2

docker-run-auth:
	docker run -d \
		-p 5000:5000 \
		--restart=always \
		-v "$(pwd)"/auth:/auth \
		-e "REGISTRY_AUTH=htpasswd" \
		-e "REGISTRY_AUTH_HTPASSWD_REALM=Registry Realm" \
		-e REGISTRY_AUTH_HTPASSWD_PATH=/auth/htpasswd \
		--name registry registry:2

create-passwd:
	docker run \
		--entrypoint htpasswd \
		httpd:2 -Bbn testuser testpassword > auth/htpasswd

registry-list:
	curl localhost:5000/v2/_catalog
