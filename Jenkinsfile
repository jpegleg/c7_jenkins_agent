pipeline {
    agent {
        label 'rpm'
    }
    

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                sh "cd /srv; rm -rf h2b2"
                git 'https://github.com/jpegleg/h2b2'
                sh "gcc h2b2.c -o h2b2 && cp h2b2 /srv/h2b2__built"
            }
            post {
                success {
                    sh "ls /srv/h2b2__built"
                }
            }
        }
        stage('Tests') {
            steps {
                // test the program
                sh "echo 123123123123 | /srv/h2b2__built > /srv/h2b2__tested"
            }
            post {
                success {
                    sh "grep 000100100011000100100011000100100011000100100011 /srv/h2b2__tested || exit 1"
                }
            }
        }
        stage('Publish') {
            steps {
                // make a tarball for pick up
                sh "tar czvf /srv/h2b2_latest_qa.tgz /srv/h2b2__built /srv/workspace/h2b2_rpm_build_pipeline/ && touch /srv/h2b2_pickup.lock"
                sh "cp /srv/h2b2_latest_qa.tgz /srv/rpmbuild/SOURCES/"
                sh "cp /srv/workspace/h2b2_rpm_build_pipeline/h2b2.spec /srv/rpmbuild/SPECS/ "
                sh "rm -f /usr/local/bin/h2b2"
                sh "cd /srv/rpmbuild && rpmbuild -ba SPECS/h2b2.spec"
            }
            post {
                success {
                    sh "ls /srv/h2b2_latest_qa.tgz || exit 1"
                    sh "cp /srv/rpmbuild/RPMS/x86_64/h2b2*.rpm /srv/ && touch /srv/h2b2_rpm_pickup.lock"
                }
            }
        }
        stage('RPM Tests') {
            steps {
                // test the rpm
                sh "yum erase -y h2b2"
                sh "rm -f /usr/local/bin/h2b2"
                sh "ls /srv/*.rpm | tail -n1 | xargs yum install -y"
            }
            post {
                success {
                    sh "ls -larth /usr/local/bin/h2b2 || exit 1"
                }
            }
        }             
    }
}
