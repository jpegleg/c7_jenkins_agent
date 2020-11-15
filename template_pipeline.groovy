pipeline {
    agent {
        label 'rpm'
    }
 // simple template
 // expand this and add more tests and more publishing into rpms etc

    stages {
        stage('Build') {
            steps {
                // pull from git
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
                sh "tar czvf /srv/h2b2_latest_qa.tgz /srv/h2b2__built && touch /srv/h2b2_pickup.lock"
            }
            post {
                success {
                    sh "ls /srv/h2b2_latest_qa.tgz || exit 1"
                }
            }
        }        
    }
}
