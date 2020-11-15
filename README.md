# c7_jenkins_agent
ansible for centos 7 jenkins agent build server baseline


After you run the ansible book to configure your CentOS 7 agent, you will need to do the agent registration and Jenkins configuration, which is not covered in this automation.

This automation installs the base packages to a CentOS 7 system for usage in package building.

The included pipeline template goes with the main jenkins instance and is a starting place for calling the CentOS 7 agent for builds.
