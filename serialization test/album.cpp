
class Album{
	public:
		Album(){
			numberOfTracks=0;
		}
		char name[512];
		char artist[512];
		string getName(){
			return string(name);
		}
		string getArtist(){
			return string(artist);
		}
		Song getTrack(int track){
			return tracks[track-1];
		}
		void addTrack(Song track){
			tracks[numberOfTracks]=track;
			numberOfTracks++;
		}
	private:
		int numberOfTracks;
		Song tracks[100];
};