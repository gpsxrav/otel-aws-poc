package com.csaa.envdashboard;

import com.csaa.envdashboard.envconfig.EnvConfig;
import com.csaa.envdashboard.envconfig.EnvConfigRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnvDashboardBootstrap implements InitializingBean {

    @Autowired
    EnvConfigRepository envConfigRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
//        List<EnvConfig> envConfigs = Arrays.asList(
//            //@formatter:off
//            new EnvConfig("E2E Environment 1","PAS","PAS","PASQA210","ISOLATED","","","Jbuild",             "PAS|PASQA210","PASQA210-Pipeline_DeployArtifacts"),
//            new EnvConfig("E2E Environment 1","PAS","DXP","DXP-PASQA210","COMBINED","Environment","PASQA210"," DxpBranchName","PAS|PASDXP","PAS-DXP-QA"),
//            new EnvConfig("E2E Environment 1","PAS","PAS-DOC","DOC-PASQA210","COMBINED","Environment","pasdoc-dcsqa5","BRANCHNAME","PAS|PASDOC","PAS-DOC-QA"),
//            new EnvConfig("E2E Environment 1","PAS","PAS-CLAIMS","","","","","","PAS|PASCLAIMS","pas-claims-pipeline-qa"),
//            new EnvConfig("E2E Environment 1","PC","IEEP-EVENTS","IEEPEVENTS","COMBINED","ENVIRONMENT","DS","TAG","PC|IEEPEVENTS","DEPLOY"),
//            new EnvConfig("E2E Environment 1","PC","IEEP-PAYMENTS","IEEPPAYMENTS","COMBINED","ENVIRONMENT","DS","TAG","PC|IEEPPAYMENTS","DEPLOY"),
//            new EnvConfig("E2E Environment 1","PC","SETTLEMENT","SETTLEMENT","COMBINED","ENVIRONMENT","DS","TAG","PC|SETTLEMENT"," DEPLOY"),
//            new EnvConfig("E2E Environment 1","CAS","CAS","CAS","ISOLATED","WHERE_TO_DEPLOY","MO","TAG","CAS|CAS-Deploy","CAS-Deploy"),
//            new EnvConfig("E2E Environment 1","DCS","DCS","DCS","ISOLATED","Deployment_Environments","DCS_PAS_BF_QA1","TAG_NAME","DCS|DCS_4.0","PAS_4.0_Deploy-DCS_PAS_QA5"),
//            new EnvConfig("E2E Environment 1","PAM","PAM","PAM","ISOLATED","environment","E2E","build.number","PAM|PAM-Deploy","PAM-Deploy"),
//            new EnvConfig("E2E Environment 2","PAS","PAS","PASQA216","ISOLATED","","","Jbuild","PAS|PASQA216","PASQA216-Pipeline_DeployArtifacts"),
//            new EnvConfig("E2E Environment 2","PAS","DXP","DXP-PASQA216","COMBINED","Environment","PASQA216","DxpBranchName","PAS|PASDXP","PAS-DXP-QA"),
//            new EnvConfig("E2E Environment 2","PAS","PAS-DOC","DOC-PASQA216","COMBINED","Environment","pasdoc-pasqa216","BRANCHNAME","PAS|PASDOC","PAS-DOC-QA"),
//            new EnvConfig("E2E Environment 2","PAS","PAS-CLAIMS","","","","","","PAS|PASCLAIMS","pas-claims-pipeline-qa"),
//            new EnvConfig("E2E Environment 2","PC","IEEP-EVENTS","IEEPEVENTS","COMBINED","ENVIRONMENT","E2E2","TAG","PC|IEEPEVENTS","DEPLOY"),
//            new EnvConfig("E2E Environment 2","PC","IEEP-PAYMENTS","IEEPPAYMENTS","COMBINED","ENVIRONMENT","E2E2","TAG","PC|IEEPPAYMENTS","DEPLOY"),
//            new EnvConfig("E2E Environment 2","PC","SETTLEMENT","SETTLEMENT","COMBINED","ENVIRONMENT","E2E2","TAG","PC|SETTLEMENT","DEPLOY"),
//            new EnvConfig("E2E Environment 2","CAS","CAS","CAS","ISOLATED","WHERE_TO_DEPLOY","MO","TAG","CAS|CAS-Deploy","CAS-Deploy"),
//            new EnvConfig("E2E Environment 2","DCS","DCS","DCS","ISOLATED","Deployment_Environments","DCS_PAS_BF_QA1","TAG_NAME","DCS|DCS_4.0","PAS_4.0_Deploy-DCS_PAS_QA5"),
//            new EnvConfig("E2E Environment 2","PAM","PAM","PAM","ISOLATED","environment","E2E","build.number","PAM|PAM-Deploy","PAM-Deploy"),
//            new EnvConfig("E2E Environment UAT","PAS","PAS","PASQA101","ISOLATED","","","Jbuild","PAS|PASQA101","PASQA101-Pipeline_DeployArtifacts"),
//            new EnvConfig("E2E Environment UAT","PAS","DXP","DXP-PASQA101","COMBINED","Environment","PASQA101","DxpBranchName","PAS|PASDXP","PAS-DXP-QA"),
//            new EnvConfig("E2E Environment UAT","PAS","PAS-DOC","DOC-PASQA101","COMBINED","Environment","pasdoc-pasqa101","BRANCHNAME","PAS|PASDOC","PAS-DOC-QA"),
//            new EnvConfig("E2E Environment UAT","PAS","PAS-CLAIMS","","","","","","PAS|PASCLAIMS","pas-claims-pipeline-qa"),
//            new EnvConfig("E2E Environment UAT","PC","IEEP-EVENTS","IEEPEVENTS","COMBINED","ENVIRONMENT","UAT","TAG","PC|IEEPEVENTS","DEPLOY"),
//            new EnvConfig("E2E Environment UAT","PC","IEEP-PAYMENTS","IEEPPAYMENTS","COMBINED","ENVIRONMENT","UAT","TAG","PC|IEEPPAYMENTS","DEPLOY"),
//            new EnvConfig("E2E Environment UAT","PC","SETTLEMENT","SETTLEMENT","COMBINED","ENVIRONMENT","UAT","TAG","PC|SETTLEMENT","DEPLOY"),
//            new EnvConfig("E2E Environment UAT","CAS","CAS","CAS","ISOLATED","WHERE_TO_DEPLOY","MO","TAG","CAS|CAS-Deploy","CAS-Deploy"),
//            new EnvConfig("E2E Environment UAT","DCS","DCS","DCS","ISOLATED","Deployment_Environments","DCS_PAS_BF_QA1","TAG_NAME","DCS|DCS_4.0","PAS_4.0_Deploy-DCS_PAS_QA5"),
//            new EnvConfig("E2E Environment UAT","PAM","PAM","PAM","ISOLATED","environment","E2E","build.number","PAM|PAM-Deploy","PAM-Deploy")
//            //@formatter:on
//        );
//        envConfigRepository.saveAll(envConfigs);
    }
}
