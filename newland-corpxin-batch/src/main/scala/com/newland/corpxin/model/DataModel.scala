
package com.newland.corpxin.model

import scala.collection.mutable

/*
定义数据样例类
 */

case class Basicdata(
                      var `corpId`: String,
                      `prevEntName`: String,
                      `startDate`: String,
                      `authority`: String,
                      `legalPerson`: String,
                      `licenseNumber`: String,
                      `district`: String,
                      `scope`: String,
                      `openStatus`: String,
                      `taxNo`: String,
                      `entType`: String,
                      `annualDate`: String,
                      `realCapital`: String,
                      `industry`: String,
                      `unifiedCode`: String,
                      `openTime`: String,
                      `regAddr`: String,
                      `regCapital`: String,
                      `orgNo`: String,
                      `addr`: String,
                      `aifanfanJumpUrl`: String,
                      `benchMark`: String,
                      `claimUrl`: String,
                      `compNum`: String,
                      `compNumLink`: String,
                      `describe`: String,
                      `districtCode`: String,
                      `email`: String,
                      `entLogo`: String,
                      `entLogoWord`: String,
                      `entName`: String,
                      `isClaim`: String,
                      `isCollected`: String,
                      `labels`: String,
                      `noRzvip`: String,
                      `oldEntName`: String,
                      `orgType`: String,
                      `paidinCapital`: String,
                      `personId`: String,
                      `personLink`: String,
                      `personLogo`: String,
                      `personLogoWord`: String,
                      `prinType`: String,
                      `scale`: String,
                      `shareLogo`: String,
                      `telephone`: String,
                      `regNo`: String,
                      `website`: String,
                      `analyse_website`: String,
                      var `ds`: String
                    ) extends Serializable

case class Basicdata2(
                       var `corpId`: String,
                       `prevEntName`: String,
                       `startDate`: String,
                       `authority`: String,
                       `legalPerson`: String,
                       `licenseNumber`: String,
                       `district`: String,
                       `scope`: String,
                       `openStatus`: String,
                       `taxNo`: String,
                       `entType`: String,
                       `annualDate`: String,
                       `realCapital`: String,
                       `industry`: String,
                       `unifiedCode`: String,
                       `openTime`: String,
                       `regAddr`: String,
                       `regCapital`: String,
                       `orgNo`: String,
                       `addr`: String,
                       `aifanfanJumpUrl`: String,
                       `benchMark`: String,
                       `claimUrl`: String,
                       `compNum`: String,
                       `compNumLink`: String,
                       `describe`: String,
                       `districtCode`: String,
                       `email`: String,
                       `entLogo`: String,
                       `entLogoWord`: String,
                       `entName`: String,
                       `isClaim`: String,
                       `isCollected`: String,
                       `labels`: mutable.Map[String, String],
                       `noRzvip`: String,
                       `oldEntName`: String,
                       `orgType`: String,
                       `paidinCapital`: String,
                       `personId`: String,
                       `personLink`: String,
                       `personLogo`: String,
                       `personLogoWord`: String,
                       `prinType`: String,
                       `scale`: String,
                       `shareLogo`: String,
                       `telephone`: String,
                       `regNo`: String,
                       `website`: String,
                       `analyse_website`: String,
                       var `ds`: String
                     )

case class Branchsdata(
                        var `corpId`: String,
                        `entName`: String,
                        `legalPerson`: String,
                        `startDate`: String,
                        `openStatus`: String,
                        `compNum`: String,
                        `compNumLink`: String,
                        `entCoreName`: String,
                        `entLink`: String,
                        `legalPersonLogo`: String,
                        `legalPersonLogoWord`: String,
                        `logo`: String,
                        `logoWord`: String,
                        `personId`: String,
                        `personLink`: String,
                        `pid`: String,
                        `regCapital`: String,
                        var `ds`: String
                      )

case class Changerecorddata(
                             var `corpId`: String,
                             `date`: String,
                             `fieldName`: String,
                             `oldValue`: String,
                             `newValue`: String,
                             var `ds`: String
                           )

case class Directorsdata(
                          var `corpId`: String,
                          `name`: String,
                          `title`: String,
                          `compNum`: String,
                          `compNumLink`: String,
                          `gender`: String,
                          `logo`: String,
                          `img`: String,
                          `logoWord`: String,
                          `personId`: String,
                          `personLink`: String,
                          var `ds`: String
                        )

case class Headcompany(
                        var `corpId`: String,
                        `entName`: String,
                        `legalPerson`: String,
                        `regCap`: String,
                        `startDate`: String,
                        `openStatus`: String,
                        `compNum`: String,
                        `compNumLink`: String,
                        `companyNum`: String,
                        `entLogo`: String,
                        `entLogoWord`: String,
                        `entUrl`: String,
                        `entWordColor`: String,
                        `legalPersonLogo`: String,
                        `legalPersonUrl`: String,
                        `legalPersonWord`: String,
                        `legalPersonWordColor`: String,
                        `personId`: String,
                        `pid`: String,
                        var `ds`: String
                      )

case class Holdsdata(
                      var `corpId`: String,
                      `entName`: String,
                      `proportion`: String,
                      `logo`: String,
                      `logoWord`: String,
                      `personId`: String,
                      `personLink`: String,
                      `pid`: String,
                      var `ds`: String
                    )

case class Investrecorddata(
                             var `corpId`: String,
                             `entName`: String,
                             `legalPerson`: String,
                             `startDate`: String,
                             `regRate`: String,
                             `regCapital`: String,
                             `openStatus`: String,
                             `compNum`: String,
                             `compNumLink`: String,
                             `entLink`: String,
                             `legalPersonLogo`: String,
                             `legalPersonLogoWord`: String,
                             `logo`: String,
                             `logoWord`: String,
                             `personId`: String,
                             `personLink`: String,
                             `pid`: String,
                             `stockLink`: String,
                             var `ds`: String
                           )

case class Shareholdersdata(
                             var `corpId`: String,
                             `name`: String,
                             `subRate`: String,
                             `subMoney`: String,
                             `paidinMoney`: String,
                             `compNum`: String,
                             `compNumLink`: String,
                             `investmentNum`: String,
                             `link`: String,
                             `logo`: String,
                             `logoWord`: String,
                             `personId`: String,
                             `personLink`: String,
                             `pid`: String,
                             `sortCol`: String,
                             var `ds`: String
                           )

case class Annualreportdata(
                             var `corpId`: String,
                             `name`: String,
                             `subRate`: String,
                             `subMoney`: String,
                             `paidinMoney`: String,
                             `compNum`: String,
                             `compNumLink`: String,
                             `investmentNum`: String,
                             `link`: String,
                             `logo`: String,
                             `logoWord`: String,
                             `personId`: String,
                             `personLink`: String,
                             `pid`: String,
                             `sortCol`: String,
                             var `ds`: String
                           )

/*
重点关注相关结构
 */
case class Abnormal(
                     var `corpId`: String,
                     `enterDate`: String,
                     `enterReason`: String,
                     `leaveDate`: String,
                     `leaveReason`: String,
                     `authority`: String,
                     `leaveAuthority`: String,
                     var `ds`: String
                   )

case class Chattelmortgage(
                            var `corpId`: String,
                            `issueDate`: String,
                            `guaranteeClaimAmount`: String,
                            `guaranteeClaimStatusCode`: String,
                            `issueAuthority`: String,
                            `licenseNumber`: String,
                            `dataId`: String,
                            `detailUrl`: String,
                            var `ds`: String
                          )

case class Clearaccount(
                         var `corpId`: String,
                         `leader`: String,
                         `employees`: String,
                         var `ds`: String
                       )

case class Discredit(
                      var `corpId`: String,
                      `implementCourt`: String,
                      `verdictNo`: String,
                      `verdictDate`: String,
                      `implementNo`: String,
                      `performStatus`: String,
                      `discreditId`: String,
                      `detailUrl`: String,
                      var `ds`: String
                    )

case class Equitypledge(
                         var `corpId`: String,
                         `dataId`: String,
                         `licenseNumber`: String,
                         `equalityPledgor`: String,
                         `equalityPawnee`: String,
                         `issueDate`: String,
                         `publicationDate`: String,
                         `equalityPledgeStatusCode`: String,
                         `detailUrl`: String,
                         `entName`: String,
                         `pid`: String,
                         var `ds`: String
                       )

case class Executedperson(
                           var `corpId`: String,
                           `date`: String,
                           `executeNumber`: String,
                           `subjectMatter`: String,
                           `court`: String,
                           `executeName`: String,
                           `pid`: String,
                           `executedId`: String,
                           `detailUrl`: String,
                           var `ds`: String
                         )

case class Filinginfo(
                       var `corpId`: String,
                       `date`: String,
                       `caseNumber`: String,
                       `court`: String,
                       `plaintiff`: String,
                       `defendant`: String,
                       `filingInfoId`: String,
                       `detailUrl`: String,
                       var `ds`: String
                     )

case class Filinginfo2(
                        var `corpId`: String,
                        `date`: String,
                        `caseNumber`: String,
                        `court`: String,
                        `plaintiff`: Array[mutable.Map[String, String]],
                        `defendant`: Array[mutable.Map[String, String]],
                        `filingInfoId`: String,
                        `detailUrl`: String,
                        var `ds`: String
                      )

case class Person(
                   name: String,
                   pid: String,
                   personId: String,
                   entUrl: String,
                   personUrl: String
                 )

case class Getcourtnoticedata(
                               var `corpId`: String,
                               `date`: String,
                               `type`: String,
                               `cause`: String,
                               `courtnoticeId`: String,
                               `court`: String,
                               `people`: String,
                               `detailUrl`: String,
                               var `ds`: String
                             )

case class Getcourtnoticedata2(
                                var `corpId`: String,
                                `date`: String,
                                `type`: String,
                                `cause`: String,
                                `courtnoticeId`: String,
                                `court`: String,
                                `people`: Array[mutable.Map[String, String]],
                                `detailUrl`: String,
                                var `ds`: String
                              )


case class Illegal(
                    var `corpId`: String,
                    `enterDate`: String,
                    `enterReason`: String,
                    `leaveDate`: String,
                    `leaveReason`: String,
                    `authority`: String,
                    `leaveAuthority`: String,
                    var `ds`: String
                  )

case class Judicialauction(
                            var `corpId`: String,
                            `date`: String,
                            `name`: String,
                            `owner`: String,
                            `description`: String,
                            `startPrice`: String,
                            `estPrice`: String,
                            `result`: String,
                            `certificate`: String,
                            `document`: String,
                            `basis`: String,
                            `restrict`: String,
                            `court`: String,
                            `ename`: String,
                            `dataId`: String,
                            `detailUrl`: String,
                            var `ds`: String
                          )

case class Lawwenshu(
                      var `corpId`: String,
                      `type`: String,
                      `verdictDate`: String,
                      `caseNo`: String,
                      `role`: String,
                      `wenshuName`: String,
                      `compName`: String,
                      `procedure`: String,
                      `wenshuId`: String,
                      `detailUrl`: String,
                      var `ds`: String
                    )

case class Opennotice(
                       var `corpId`: String,
                       `hearingDate`: String,
                       `caseNo`: String,
                       `caseReason`: String,
                       `judge`: String,
                       `court`: String,
                       `tribunal`: String,
                       `plaintifflist`: String,
                       `defendantlist`: String,
                       `ename`: String,
                       `pureRole`: String,
                       `dataId`: String,
                       `content`: String,
                       `region`: String,
                       `department`: String,
                       `author`: String,
                       `judgeType`: String,
                       `detailUrl`: String,
                       var `ds`: String
                     )

case class Opennotice2(
                        var `corpId`: String,
                        `hearingDate`: String,
                        `caseNo`: String,
                        `caseReason`: String,
                        `judge`: String,
                        `court`: String,
                        `tribunal`: String,
                        `plaintifflist`: Array[String],
                        `defendantlist`: Array[String],
                        `ename`: String,
                        `pureRole`: String,
                        `dataId`: String,
                        `content`: String,
                        `region`: String,
                        `department`: String,
                        `author`: String,
                        `judgeType`: String,
                        `detailUrl`: String,
                        var `ds`: String
                      )

case class Penalties(
                      var `corpId`: String,
                      `penaltiesNumber`: String,
                      `penaltiesName`: String,
                      `penaltiesType`: String,
                      `penalties`: String,
                      `penaltiesDate`: String,
                      `penaltiesId`: String,
                      `penaltiesReason`: String,
                      `detailUrl`: String,
                      var `ds`: String
                    )

case class Restrictedconsumer(
                               var `corpId`: String,
                               `releaseDate`: String,
                               `personName`: String,
                               `companyName`: String,
                               `execComapnyName`: String,
                               `court`: String,
                               `dataId`: String,
                               `personLogoWord`: String,
                               personLogoWordRandNum: String,
                               `detailUrl`: String,
                               `companyUrl`: String,
                               `pid`: String,
                               var `ds`: String
                             )

case class Simplecancellation(
                               var `corpId`: String,
                               `entName`: String,
                               `creditNo`: String,
                               `noticePeriodDate`: String,
                               `departMent`: String,
                               `gsScaObjections`: String,
                               `cancelId`: String,
                               `cancelImageUrl`: String,
                               `detailUrl`: String,
                               `gsScaResult`: String,
                               var `ds`: String
                             )

case class Simplecancellation2(
                                var `corpId`: String,
                                `entName`: String,
                                `creditNo`: String,
                                `noticePeriodDate`: String,
                                `departMent`: String,
                                `gsScaObjections`: Array[mutable.Map[String, String]],
                                `cancelId`: String,
                                `cancelImageUrl`: String,
                                `detailUrl`: String,
                                `gsScaResult`: Array[mutable.Map[String, String]],
                                var `ds`: String
                              )

case class Stockfreeze(
                        var `corpId`: String,
                        `beExecutedPerson`: String,
                        `equalityAmount`: String,
                        `notificationNumber`: String,
                        `type`: String,
                        `status`: String,
                        `typeAndStatus`: String,
                        `stockFreezeKey`: String,
                        `detailUrl`: String,
                        var `ds`: String
                      )

case class Taxviolation(
                         var `corpId`: String,
                         `name`: String,
                         `penaltyType`: String,
                         `reportDate`: String,
                         `regCode`: String,
                         `dataId`: String,
                         `agenceEmployee`: String,
                         `detailUrl`: String,
                         var `ds`: String
                       )

case class Terminationcase(
                            var corpId: String,
                            filingDate: String,
                            caseNoTerminal: String,
                            name: String,
                            amount: String,
                            court: String,
                            terminateDate: String,
                            nameUrl: String,
                            pid: String,
                            dataId: String,
                            detailUrl: String,
                            var ds: String
                          )


/*
经营状况相关结构
 */
case class Doublecheckup(
                          var `corpId`: String,
                          `insDate`: String,
                          `insauth`: String,
                          `raninsPlaneName`: String,
                          `raninsTypeName`: String,
                          `raninsPlanId`: String,
                          var `ds`: String
                        )

case class Foodquality(
                        var `corpId`: String,
                        `detailUrl`: String,
                        `insId`: String,
                        `notificationDate`: String,
                        `notificationNum`: String,
                        `productName`: String,
                        `qualityId`: String,
                        `result`: String,
                        `type`: String,
                        `detail`: String,
                        var `ds`: String
                      )

case class Foodquality2(
                         var `corpId`: String,
                         `detailUrl`: String,
                         `insId`: String,
                         `notificationDate`: String,
                         `notificationNum`: String,
                         `productName`: String,
                         `qualityId`: String,
                         `result`: String,
                         `type`: String,
                         `detail`: mutable.Map[String, String],
                         var `ds`: String
                       )

case class License(
                    var `corpId`: String,
                    `issueAuthority`: String,
                    `licenseContent`: String,
                    `licenseName`: String,
                    `licenseNumber`: String,
                    `validityFrom`: String,
                    `validityTo`: String,
                    var `ds`: String
                  )

case class Quality(
                    var `corpId`: String,
                    `samlingYears`: String,
                    `samlingBatch`: String,
                    `productName`: String,
                    `samplingResult`: String,
                    `detailUrl`: String,
                    `insId`: String,
                    `qualityId`: String,
                    `type`: String,
                    `detail`: String,
                    var `ds`: String
                  )

case class Quality2(
                     var `corpId`: String,
                     `samlingYears`: String,
                     `samlingBatch`: String,
                     `productName`: String,
                     `samplingResult`: String,
                     `detailUrl`: String,
                     `insId`: String,
                     `qualityId`: String,
                     `type`: String,
                     `detail`: mutable.Map[String, String],
                     var `ds`: String
                   )

case class Randominspection(
                             var `corpId`: String,
                             `inspectionAuthority`: String,
                             `inspectionDate`: String,
                             `inspectionResult`: String,
                             `inspectionType`: String,
                             var `ds`: String
                           )

/*
知识产权相关结构
 */
case class Copyright(
                      var `corpId`: String,
                      `softwareName`: String,
                      `shortName`: String,
                      `batchNum`: String,
                      `softwareType`: String,
                      `typeCode`: String,
                      `regDate`: String,
                      `softId`: String,
                      `detailUrl`: String,
                      `detail`: String,
                      var `ds`: String
                    )

case class Copyright2(
                       var `corpId`: String,
                       `softwareName`: String,
                       `shortName`: String,
                       `batchNum`: String,
                       `softwareType`: String,
                       `typeCode`: String,
                       `regDate`: String,
                       `softId`: String,
                       `detailUrl`: String,
                       `detail`: mutable.Map[String, String],
                       var `ds`: String
                     )

case class Icpinfo(
                    var `corpId`: String,
                    `siteName`: String,
                    `homeSite`: String,
                    `domain`: String,
                    `icpNo`: String,
                    var `ds`: String
                  )

case class Icpinfo2(
                     var `corpId`: String,
                     `siteName`: String,
                     `homeSite`: Array[String],
                     `domain`: Array[String],
                     `icpNo`: String,
                     var `ds`: String
                   )

case class Mark(
                 var `corpId`: String,
                 `markStyle`: String,
                 `markName`: String,
                 `markRegNo`: String,
                 `applyDate`: String,
                 `markType`: String,
                 `markLogoWord`: String,
                 `dataId`: String,
                 `detailUrl`: String,
                 var `ds`: String
               )

case class Patent(
                   var corpId: String,
                   patentName: String,
                   publicationNumber: String,
                   patentType: String,
                   publicationDate: String,
                   referId: String,
                   pid: String,
                   detailUrl: String,
                   var ds: String
                 )

case class Workright(
                      var `corpId`: String,
                      `registrationNo`: String,
                      `type`: String,
                      `name`: String,
                      `completionDate`: String,
                      `publicationDate`: String,
                      `registrationDate`: String,
                      var `ds`: String
                    )
