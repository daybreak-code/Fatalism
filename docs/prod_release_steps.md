Release Info

| Item                    | Information         |
|-------------------------|---------------------|
| GCP Product Envirnoment | https://link        |
| Release Approach        | Software Deployment |
| Service Interruption    | Non-Disruptive      |
| CR Gategory             | Non-Disruptive      |
| APP Name                | cosmic-service      |
| Existing Image          | 1.10.5              |
| New Image               | 1.10.6              |
| Release Tool            | Jenkins             |


RunBook

| SN  | Type                             | Short Description                                                                                                                                                                           | Description                                                                                                                    | Duration |
|-----|----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------|----------|
| 1   | Implementation                   | Dual Control                                                                                                                                                                                | Dual Control on implementation, implementer: Pod TL and CR Assigner                                                            | 10mins   |
| 2   | Implementation                   | Promote image to prod via Jenkins Pipeline                                                                                                                                                  | Use Jenkins pipeline to promote application docker image to cosmic Prod CAR                                                    | 10mins   |
| 3   | Implementation                   | DB backup via Jenkins Pipeline                                                                                                                                                              | Trigger below Jenkins pipeline to backup DB before new DB script deployment                                                    | 10mins   |
| 4   | Implementation                   | Deploy DB script via Jenkins Pipeline                                                                                                                                                       | Deploy DB script via below Jenkins Pipeline: https://jenkinslink                                                               | 10mins   |
| 5   | Release                          | Jenkins Release                                                                                                                                                                             | API Deployment with New Image Version mentioned in "implementation plan" via jenkins pipeline, jenkins job: https:/jenkinslink | 10mins   |
| 6   | Post Implementation Verification | IT Health check                                                                                                                                                                             | IT to trigger PDV pipeline to further check if application version and health check correct, jenkins job: https:/pdvlink       | 10mins   |
| 7   | Post Implementation Verification | Go/No-Go Task                                                                                                                                                                               | Go no go decision after IT health check                                                                                        | 30mins   |
| 8   | Backout if need                  | Backout if need(If Decision as No Go) 1. UCM IT team to trigger API deployment with previous Image version mentioned in "Backout plan" with below Jenkins pipeline.<br/> 2. IT Health check | Go no go decision after IT health check                                                                                        | 30mins   |


