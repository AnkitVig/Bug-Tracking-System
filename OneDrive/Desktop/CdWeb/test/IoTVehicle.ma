[top]
components : Gateway@Gateway
components : Iot@Iot
components : Manufacturing
out : auto_contactNum
out : auto_appointTime
out : auto_location
in : autoIN
Link : autoIN GateIN@Gateway
Link : GateOUT@Gateway IotIN@Iot
Link : IotOUT@Iot manufIN@Manufacturing
Link : manuf_contactNum@Manufacturing auto_contactNum
Link : manuf_appointTime@Manufacturing auto_appointTime
Link : manuf_location@Manufacturing auto_location

[Gateway]
[Iot]

[Manufacturing]
components : IssueAnalyzer@IssueAnalyzer
components : AssetManagement
out : manuf_contactNum
out : manuf_appointTime
out : manuf_location
in : manufIN
Link : manufIN anaIN@IssueAnalyzer
Link : anaSim@IssueAnalyzer assetIN@AssetManagement
Link : asset_contactNum@AssetManagement manuf_contactNum
Link : asset_appointTime@AssetManagement manuf_appointTime
Link : asset_location@AssetManagement manuf_location

[IssueAnalyzer]

[AssetManagement]
components : IssueSimulator@IssueSimulator
components : Checklist@Checklist
components : Appt@Appt
components : InfoSender@InfoSender
out : asset_contactNum
out : asset_appointTime
out : asset_location
in : assetIN
Link : assetIN simIN@IssueSimulator
Link : simOUT@IssueSimulator checkIN@Checklist
Link : checkOUT@Checklist apptIN@Appt
Link : apptOUT@Appt infoIN@InfoSender
Link : contactNum@InfoSender asset_contactNum
Link : appointTime@InfoSender asset_appointTime
Link : location@InfoSender asset_location

[IssueSimulator]
[Checklist]
[Appt]
[InfoSender]
