### IoT Analytics

#### Requirements:
Demo is bundled in a self-contained package. In order to build it from sources you will need:

 - git
 - docker
 - docker-compose

 Recommended resources allocated to Docker:

 - 4 CPUs
 - 8GB RAM

 You can checkout the repository and run the demo locally.

#### How to run:

In order to run the demo locally, execute the following commands which build the project from sources and start all required services, including the Apache Flink and Apache Kafka clusters.

```bash
git clone https://scm.chelsea.kt.co.kr/10150555/fraud-detection-demo.git
docker build -t demo-fraud-webapp:latest -f webapp/webapp.Dockerfile webapp/
docker build -t flink-job-fraud-demo:latest -f flink-job/Dockerfile flink-job/
docker-compose -f docker-compose-local-job.yaml up
```

#### 기본 세팅 for Test codes(DB가 비어있을 때):
> 추후에 Python Script로 만들어 놓을 계획이지만, 지금은 아래의 과정을 따라 써야 함
1. User 정보 등록
```JSON
{
    "email": "root",
    "password": "root",
    "name": "root"
}
```
2. Topic 정보 등록

    1. topic get 날려서 나오는 결과 복사
    2. topic post에 붙여 넣기

3. Filter 정보 등록

    1. livetransactions와 alerts에 하나씩만 등록하면 됨
    ```JSON
    {
        "field": "testing"
    }
    ```

4. Datasource 조회
5. Datasource 메타데이터 조회



__Note__: Dependencies are stored in a cached Docker layer. If you later only modify the source code, not the dependencies, you can expect significantly shorter packaging times for the subsequent builds.

When all components are up and running, go to `localhost:5656` in your browser.

__Note__: you might need to change exposed ports in _docker-compose-local-job.yaml_ in case of collisions.

